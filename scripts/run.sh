./build.sh
cd ../build || exit #TODO: Add to classpath
java -cp "$JDK_18/lib/tools.jar;." dev/alexengrig/myjd/Debugger
cd ../scripts || exit #TODO: Workaround to remove build