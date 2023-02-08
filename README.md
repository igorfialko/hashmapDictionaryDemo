# Install and run

 >
 > curl https://raw.githubusercontent.com/FinNLP/humannames/master/index.json >> NameSurname.json
 > curl https://www.gutenberg.org/cache/epub/69962/pg69962.txt >> book
 > JAVA_OPTS="-Xmx16096M -XX:+UseG1GC" sbt run
 >
# Output

 > Loading the text (1142 MB) in memory...
 >
 > Loading the text (1142 MB) in memory took 17.7740 seconds
 >
 > Cleaning and transforming the textCleaning and transforming the text took 17.4370 seconds
 >
 > Loading the dictionary in memory...
 >
 > Loading the dictionary in memory took 0.1370 seconds
 >
 > Transforming dictionary to hashmap
 >
 > Transforming dictionary to hashmap took 0.2080 seconds
 >
 > Locating names in text
 >
 > Locating names in text took 2.8370 seconds
 >
 > Found 89984270 names in 158627760 words long text.
 >
