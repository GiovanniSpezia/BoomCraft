# 💣 BoomCraft

A modern, powerful and optimized explosion plugin for Minecraft servers.

---

## ✨ Features

- 💥 Custom explosion system  
- ⚡ Configurable explosion types (`config.yml`)  
- 🧨 Custom TNT with special effects  
- 🎯 Explosions at player or custom coordinates  
- 🔢 Custom explosion power support  
- 🌪 Shockwave system (knockback players)  
- ⚙️ Fully configurable behavior  
- 🔐 Permission-based system  
- 🧠 Optimized and lightweight  

---

## 📌 Commands

| Command | Description |
|--------|------------|
| `/boom` | Show plugin help |
| `/boom <type>` | Explosion on yourself |
| `/boom <type> <power>` | Explosion with custom power |
| `/boom <type> <x> <y> <z>` | Explosion at coordinates |
| `/boom <type> <power> <x> <y> <z>` | Power + coordinates |
| `/boom give <type>` | Get custom TNT |

---

## 💥 Explosion Types

| Type | Description |
|------|------------|
| `normal` | Standard explosion |
| `fire` | Explosion with fire |
| `shockwave` | Pushes players away |

---

## 🔐 Permissions

| Permission | Description |
|-----------|------------|
| `boom.use` | Use explosion commands |
| `boom.admin` | Access admin commands (give TNT) |

---

## ⚙️ Configuration (`config.yml`)

```yaml
explosions:
  NORMAL:
    power: 4
    fire: false
    breakBlocks: true

  FIRE:
    power: 4
    fire: true
    breakBlocks: true

  SHOCKWAVE:
    power: 4
    fire: false
    breakBlocks: false
    knockback: 2
```

## ⚙️ Compatibility

Minecraft 1.21+

Spigot / Paper

Java 17 / 21 (recommended: 21)

---

## 🚀 Installation

Download the latest release

Put BoomCraft.jar into /plugins

Start or restart the server

---

🛠️ Build
Maven
```
mvn clean package
```

---

💥 Example Usage
```
/boom normal
/boom fire 10
/boom shockwave 100 64 200
/boom normal ~ ~ ~
/boom give normal
```

---

🧑‍💻 Author

giospezia.it
