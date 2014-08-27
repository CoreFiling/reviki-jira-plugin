# JIRA Reviki Plugin

This plugin brings the [Reviki][reviki] wiki syntax to JIRA, allowing
comments and the like to be written using it. A CSS file providing the
default JHighlighter styles is also included.

## Running in the development environment

Clone this repo to the same place as the JIRA installation, e.g.,

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

## Packaging

    ./fetch-reviki-sh
    atlas-mvn package

The plugin is now available at `target/reviki-renderer-1.0-SNAPSHOT.jar`.

## Integration with Confluence

### Confluence in Reviki

By default, this also renders Confluence style
`[single square brackets]` links, in addition to Reviki-style
`[[double square brackets]]` links.

In order to disable this behaviour, see the plugin configuration
(accessible through the admin panel).

### Reviki in Confluence

This plugin also provides a Confluence macro, `{reviki}`, for
rendering a block of markup with Reviki syntax, for example:

    {reviki}**bold //italic --strike--//**{reviki}

    Regular _Confluence_ markup.

## Licensing

This plugin is made available under the Apache 2.0 license, because
Reviki is and so it seemed convenient.

[reviki]: https://github.com/CoreFiling/reviki/
