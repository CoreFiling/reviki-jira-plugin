#!/bin/bash

tmpdir=`mktemp -d`

# Build the renderer jar
pushd $tmpdir

git clone https://github.com/CoreFiling/reviki .

ant renderer-jar

popd

# Install it to the local maven repository

# Even though the pom is not needed, mvn still complains (because it can't find the atlassian stuff)
tmppom=`mktemp`
mv pom.xml $tmppom

RENDERER_JAR=$tmpdir/ant-build/web/WEB-INF/lib/reviki-renderer.jar
VERSION=`unzip -p $RENDERER_JAR META-INF/MANIFEST.MF | sed -n 's/^Implementation-Version: \([^ ]*\) .*/\1/p'`

atlas-mvn install:install-file -DlocalRepositoryPath=repo \
                         -DcreateChecksum=true \
                         -Dpackaging=jar \
                         -Dfile=$RENDERER_JAR \
                         -DgroupId=net.hillsdon.reviki \
                         -DartifactId=reviki-renderer \
                         -Dversion=$VERSION

cp repo/net/hillsdon/reviki/reviki-renderer/maven-metadata-local.xml repo/net/hillsdon/reviki/reviki-renderer/maven-metadata.xml

mv $tmppom pom.xml

# Clean up
rm -rf $tmpdir $tmppom
