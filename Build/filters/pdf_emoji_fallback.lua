-- Ersetzt Emoji-Zeichen, die DejaVu Sans im XeLaTeX-PDF-Build nicht enthält.
-- Die Markdown-Quellen sowie DOCX/HTML/PPTX bleiben unverändert.

local replacements = {
  ["🧪"] = "Experiment",
  ["📖"] = "Aufgabe",
  ["🤖"] = "Roboter",
  ["💡"] = "Hinweis",
  ["🚀"] = "Start",
  ["💬"] = "Gespräch",
  ["✅"] = "Ja",
  ["❌"] = "Nein",
}

function Str(el)
  local text = el.text
  local changed = false

  for emoji, replacement in pairs(replacements) do
    if text:find(emoji, 1, true) then
      text = text:gsub(emoji, replacement)
      changed = true
    end
  end

  if changed then
    return pandoc.Str(text)
  end
end
