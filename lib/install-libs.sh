mvn install:install-file \
  -DgroupId=com.google.code.java-allocation-instrumenter \
  -DartifactId=java-allocation-instrumenter \
  -Dversion=2.0 \
  -Dfile=`dirname $0`/allocation.jar \
  -Dpackaging=jar \
  -DgeneratePom=true

mvn install:install-file \
  -DgroupId=com.google.caliper \
  -DartifactId=caliper \
  -Dversion=1.0-SNAPSHOT \
  -Dfile=`dirname $0`/caliper-r294.jar \
  -Dpackaging=jar \
  -DgeneratePom=true
