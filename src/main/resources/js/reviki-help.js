var popupOptions = {
  toolbar: 'no',
  location: 'no',
  directories: 'no',
  status: 'yes',
  menubar: 'no',
  scrollbars: 'yes',
  resizable: 'yes',
  width: 800,
  height: 620
};

AJS.toInit(function() {
  var init = function(e, context) {
    var showHelp = function() {
      var options = AJS.$.map(popupOptions, function(value, key) {
        return key + "=" + value;
      }).join(",");
      window.open(contextPath + "/RevikiHelpAction.jspa", "reviki_renderer_notation_help", options);
    }

    AJS.$("a.reviki.help-icon").bind("click", function (event) {
      event.preventDefault();
      showHelp();
    });
  }

  if (JIRA.Events && JIRA.Events.NEW_CONTENT_ADDED) {
    JIRA.bind(JIRA.Events.NEW_CONTENT_ADDED, init);
  }
  else {
    init();
  }
});
