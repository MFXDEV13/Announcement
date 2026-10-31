# Announcement

Plugin broadcast announcement ringan untuk server **Paper 26.x** — pesan otomatis bergiliran, command `/announce` manual, dan dukungan penuh **MiniMessage**. Setup 1 menit, tanpa dependency tambahan.

![Paper](https://img.shields.io/badge/Paper-26.2-blue)
![Java](https://img.shields.io/badge/Java-25-orange)
![Version](https://img.shields.io/badge/version-1.0.0-green)
![License](https://img.shields.io/badge/license-MIT-lightgrey)

## Fitur

- **Auto broadcast** — pesan diputar bergiliran sesuai interval yang bisa diatur.
- **Manual broadcast** — kirim pengumuman langsung lewat `/announce <pesan>`.
- **MiniMessage support** — warna, gradient, bold, click/hover event, dan lainnya tanpa kode warna legacy.
- **Prefix global** — teks depan pesan yang bisa dikustomisasi.
- **Ringan** — tanpa dependency eksternal, hanya Paper API.

## Instalasi

1. Pastikan server menggunakan **Paper 26.2+** dan **Java 25**.
2. Download file `Announcement-1.0.0.jar` dari halaman [Releases](../../releases).
3. Letakkan di folder `plugins/` server.
4. Restart server, lalu edit `plugins/Announcement/config.yml`.
5. Restart server untuk menerapkan perubahan config.

## Command & Permission

| Command | Deskripsi | Permission |
| --- | --- | --- |
| `/announce <pesan>` | Broadcast pesan ke seluruh server | `announcement.use` |

Permission `announcement.use` default-nya hanya untuk **OP**.

Contoh:

```
/announce <gold>Event dimulai dalam 5 menit!</gold>
```

## Konfigurasi

`config.yml` mendukung **MiniMessage** di semua field teks.

```yaml
# Teks di depan setiap pesan. Mendukung MiniMessage.
prefix: "<gray>[<gold>Announcement<gray>]</gray> "

# Jarak antar broadcast otomatis, dalam detik.
interval-seconds: 300

# Pesan diputar bergiliran sesuai urutan. Mendukung MiniMessage.
messages:
  - "<yellow>Selamat datang di server!</yellow>"
  - "<green>Gunakan <white>/help</white> untuk melihat command.</green>"
  - "<aqua>Join Discord kami: <white>discord.gg/contoh</white></aqua>"
```

> Jika `messages` kosong, broadcast otomatis akan dinonaktifkan (command manual tetap berfungsi).

## Contoh MiniMessage

```yaml
messages:
  - "<gradient:#00ffcc:#0099ff><bold>Selamat datang!</bold></gradient>"
  - "<click:run_command:/help><hover:show_text:'Klik untuk buka help'><green>Klik untuk bantuan</green></hover></click>"
  - "<red>⚠ Restart server dalam 10 menit!</red>"
```

Referensi lengkap: [MiniMessage Docs](https://docs.advntr.dev/minimessage/format.html).

## Build dari Source

Butuh **JDK 25** dan **Maven**.

```bash
mvn clean package
```

Hasil build ada di `target/Announcement-1.0.0.jar`.

## Struktur Project

```
src/main/java/com/devkry/announcement/
├── AnnouncementPlugin.java        # Entry point plugin
├── command/AnnounceCommand.java   # Handler /announce
└── manager/AnnouncementManager.java # Scheduler & logika broadcast
```

## Lisensi

Dirilis di bawah lisensi [MIT](LICENSE).
