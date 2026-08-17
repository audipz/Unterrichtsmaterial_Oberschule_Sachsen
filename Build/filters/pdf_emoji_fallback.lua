-- Ersetzt Zeichen, die DejaVu Sans bzw. DejaVu Sans Mono im XeLaTeX-PDF-Build
-- nicht enthält. Die Markdown-Quellen sowie DOCX/HTML/PPTX bleiben unverändert.
--
-- Der Filter behandelt sowohl normalen Text (Str) als auch Code/CodeBlock.
-- Letzteres ist wichtig, weil Pandoc Code mit DejaVu Sans Mono setzt.

local replacements = {
  ["🧪"] = "Experiment",
  ["📖"] = "Aufgabe",
  ["🤖"] = "Roboter",
  ["💡"] = "Hinweis",
  ["🚀"] = "Start",
  ["💬"] = "Gespräch",
  ["✅"] = "Ja",
  ["❌"] = "Nein",
  ["😊"] = ":-)",
  ["中"] = "ZHONG",
}

local function replace_unsupported(text)
  for character, replacement in pairs(replacements) do
    text = text:gsub(character, replacement)
  end
  return text
end

function Str(el)
  local text = replace_unsupported(el.text)
  if text ~= el.text then
    return pandoc.Str(text)
  end
end

function Code(el)
  el.text = replace_unsupported(el.text)
  return el
end

function CodeBlock(el)
  el.text = replace_unsupported(el.text)
  return el
end
