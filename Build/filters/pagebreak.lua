function Header(el)
  if el.level ~= 1 then
    return nil
  end

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
end
