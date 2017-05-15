function highlight() {
  AJS.$('pre.wiki-content code').each(function(i, block) {
    hljs.highlightBlock(block);
  });

  AJS.$('code.wiki-content.inline').each(function(i, block) {
    hljs.highlightBlock(block);
  });
}

AJS.toInit(function() {
  // initial rendered reviki code markup on pageload
  highlight();

  // highlight rendered reviki code markup in preview
  AJS.$(document).on("showWikiPreview", function() {
    highlight();
  });

  // highlight rendered reviki code markup after inline edits
  JIRA.bind(JIRA.Events.NEW_CONTENT_ADDED, function(e, context, reason) {
    highlight();
  });
});
