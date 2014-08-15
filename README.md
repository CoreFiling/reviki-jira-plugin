# JIRA Reviki Plugin

This plugin brings the [Reviki][reviki] wiki syntax to JIRA, allowing
comments and the like to be written using it. A CSS file providing the
default JHighlighter styles is also included.

## Running

I've only run this in the development environment, so install the
Atlassian SDK, set up a JIRA development instance, and clone this repo
to a the same place as the JIRA installation, e.g.,

    atlas/
      - amps-standalone/
          - ...
      - jira-reviki-plugin/
          - ...

You can then `cd` into the clone and run it, after first pulling in
the renderer jar,

    cd jira-reviki-plugin
    ./fetch-reviki.sh
    atlas-run

## Licensing

This plugin is made available under the Apache 2.0 license, because
Reviki is and so it seemed convenient.

[reviki]: https://github.com/CoreFiling/reviki/
