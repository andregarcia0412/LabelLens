import os
import re
import json
import flask
from unidecode import unidecode
from google.cloud import vision

app = flask.Flask(__name__)

def analyse_vision_AI(image_bytes: bytes) -> dict:

    client = vision.ImageAnnotatorClient()
    image = vision.Image(content=image_bytes)
    response = client.document_text_detection(image=image)

    if response.error.message:
        raise Exception(f'{response.error.message}\nPara mais informações, verifique a documentação da API.')
    
    if response.full_text_annotation.text:
        raw_text = unidecode(response.full_text_annotation.text)
        parsed_data = parse_table(raw_text)
        return parsed_data
    else:
        return "Erro: nenhum texto detectado"

    

def parse_table(text):
    keywords = ["Valor energetico", "Carboidratos", "Acucares totais", "Acucares adicionados", "Proteinas", "Gorduras totais", "Gorduras saturadas", "Gorduras trans", "Fibras alimentares", "Sodio", "Calcio"]

    all_lines = text.split('\n')
    cleaned_lines = []
    junk_terms = ["Porcoes por", "cerca de", "Percentual de valores"]

    for line in all_lines:
        line = line.strip()
        if line and not any(junk in line for junk in junk_terms):
            cleaned_lines.append(line)

    data = {}
    data["Acucares totais"] = {
        "por_100g":"0",
        "por_porcao":"0",
        "vd_porcao":"-"
    }
    
    for i, line in enumerate(cleaned_lines):
        for keyword in keywords:
            if keyword in line:
                collected_numbers = []
                for lookahead_index in range(i+1, min(i+5, len(cleaned_lines))):
                    next_line = cleaned_lines[lookahead_index]
                    if any(kw in next_line for kw in keywords):
                        break
                    numbers_in_line = re.findall(r'[\d\.,]+', next_line)
                    if numbers_in_line:
                        collected_numbers.extend(numbers_in_line)
                        if keyword == "Acucares totais" and len(collected_numbers) == 2:
                            data[keyword] = {
                                "por_100g":collected_numbers[0],
                                "por_porcao":collected_numbers[1],
                                "vd_porcao":"-"
                            }


                if len(collected_numbers) >= 3:
                    nutrient_name = line.replace("NUTRICIONAL ", "").strip()
                    if nutrient_name != "Acucares totais":
                        data[nutrient_name] = {
                                "por_100g":collected_numbers[0],
                                "por_porcao":collected_numbers[1],
                                "vd_porcao":collected_numbers[2] + "%"
                            }
                    break
    return data

@app.route('/analyse_label', methods=["POST"])
def handle_image_upload():
    if 'image' not in flask.request.files:
        return flask.jsonify({"Erro": "Nenhuma imagem enviada"}), 400
    
    image_file = flask.request.files["image"]
    image_bytes = image_file.read()

    try:
        result_data = analyse_vision_AI(image_bytes)
        return flask.jsonify(result_data)
    except Exception as e:
        print(f"Ocorreu um erro: {e}")
        return flask.jsonify({f"Erro interno do servidor: {e}"}), 500

if __name__ == '__main__':

    app.run(host = "0.0.0.0", port=int(os.environ.get("PORT", 8080)))
