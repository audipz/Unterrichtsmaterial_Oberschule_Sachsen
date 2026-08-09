-- Insert a page break before every level-1 heading.
-- This keeps major source sections on fresh pages in DOCX/PDF/print HTML.

local first_h1_seen = false

function Header(el)
  if el.level ~= 1 then
    return nil
  end

  -- Every H1 belongs to the body. Pandoc's title and TOC are generated
  -- outside the body, so a break here starts the first chapter after TOC.
  if FORMAT:match("docx") then
    return {
      pandoc.RawBlock("openxml",
        '<w:p><w:r><w:br w:type="page"/></w:r></w:p>'),
      el
    }
  elseif FORMAT:match("latex") then
    return {
      pandoc.RawBlock("latex", "\\clearpage"),
      el
    }
  elseif FORMAT:match("html") then
    return {
      pandoc.RawBlock("html", '<div class="page-break"></div>'),
      el
    }
  end

  return nil
end
