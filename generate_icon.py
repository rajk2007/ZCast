from PIL import Image, ImageDraw, ImageFont
import os

def generate_icon(size, path):
    img = Image.new("RGBA", (size, size), color=(10, 10, 15, 255))
    draw = ImageDraw.Draw(img)
    font_size = int(size * 0.65)
    try:
        font = ImageFont.truetype("/usr/share/fonts/truetype/dejavu/DejaVuSans-Bold.ttf", font_size)
    except:
        font = ImageFont.load_default()
    text = "N"
    bbox = draw.textbbox((0, 0), text, font=font)
    text_width = bbox[2] - bbox[0]
    text_height = bbox[3] - bbox[1]
    x = (size - text_width) / 2
    y = (size - text_height) / 2 - bbox[1]
    draw.text((x, y), text, fill=(0, 229, 255, 255), font=font)
    os.makedirs(os.path.dirname(path), exist_ok=True)
    img.save(path)
    print(f"Generated: {path}")

sizes = {
    "app/src/main/res/mipmap-mdpi/ic_launcher.png": 48,
    "app/src/main/res/mipmap-mdpi/ic_launcher_round.png": 48,
    "app/src/main/res/mipmap-hdpi/ic_launcher.png": 72,
    "app/src/main/res/mipmap-hdpi/ic_launcher_round.png": 72,
    "app/src/main/res/mipmap-xhdpi/ic_launcher.png": 96,
    "app/src/main/res/mipmap-xhdpi/ic_launcher_round.png": 96,
    "app/src/main/res/mipmap-xxhdpi/ic_launcher.png": 144,
    "app/src/main/res/mipmap-xxhdpi/ic_launcher_round.png": 144,
    "app/src/main/res/mipmap-xxxhdpi/ic_launcher.png": 192,
    "app/src/main/res/mipmap-xxxhdpi/ic_launcher_round.png": 192,
}

for path, size in sizes.items():
    generate_icon(size, path)

print("All icons generated!")
