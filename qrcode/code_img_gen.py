import xlrd
import json
import PIL
from PIL import Image
from PIL import ImageDraw
from PIL import ImageFont
import qrcode

season = "winter"

file_path = season + ".xlsx"
data = xlrd.open_workbook(file_path)
table = data.sheet_by_name("Sheet1")

rows_num = table.nrows
cols_num = table.ncols

keys = table.row_values(0)

file = open("2018" + season + "\\" + season + ".json", "w")

text_font = ImageFont.truetype('C:\\Windows\\Fonts\\simhei.ttf', 24)

for i in range(1, rows_num):
    values = table.row_values(i)
    json_dict = {}
    for j in range(cols_num):
        if keys[j] == 'total':
            json_dict[keys[j]] = int(values[j])
        else:
            json_dict[keys[j]] = values[j]
    json_text = json.dumps(json_dict, ensure_ascii=False)
    print(json_text)
    file.write(json_text)
    file.write("\n")

    qr = qrcode.QRCode(
        version=1,
        error_correction=qrcode.constants.ERROR_CORRECT_L,
        box_size=10,
        border=4,
    )

    file_name = '2018' + season + '\\' + json_dict['name'] + '.png'
    insert_text = json_dict['name']

    qr.add_data(json_text)
    qr.make(fit=True)
    img = qr.make_image(fill_color="black", back_color="white")
    img.save(file_name)

    image = Image.open(file_name)
    draw = ImageDraw.Draw(image)
    image_width, image_height = image.size
    text_width, text_height = draw.textsize(insert_text.encode('utf-8'))

    draw.text((10,10), insert_text, font=text_font)

    image.save(file_name)

    print(i, '/', str(rows_num - 1))

file.close()