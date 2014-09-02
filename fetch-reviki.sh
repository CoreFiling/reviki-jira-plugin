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

mvn install:install-file -DlocalRepositoryPath=repo \
                         -DcreateChecksum=true \
                         -Dpackaging=jar \
                         -Dfile=$tmpdir/ant-build/web/WEB-INF/lib/reviki-renderer.jar \
                         -DgroupId=net.hillsdon.reviki \
                         -DartifactId=reviki-renderer \
                         -Dversion=1.0.0

mv $tmppom pom.xml

# Clean up
rm -rf $tmpdir $tmppom
