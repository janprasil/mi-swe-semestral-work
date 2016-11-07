export JAVA_HOME=$(/usr/libexec/java_home)
export PATH=$JAVA_HOME/jre/bin:$PATH
sh ../../../../tarql/target/appassembler/bin/tarql triplifier-postcodes.tarql ../catalog/postcodes_dataset.csv > ../output/postcodes_output.ttl
sh ../../../../tarql/target/appassembler/bin/tarql -H triplifier-prosperity_index.tarql ../catalog/prosperity_index_dataset.csv > ../output/prosperity_index_output.ttl
sh ../../../../tarql/target/appassembler/bin/tarql -H triplifier-eu_referendum.tarql ../catalog/eu_referendum_dataset.csv > ../output/eu_referendum_output.ttl
sh ../../../../tarql/target/appassembler/bin/tarql -H triplifier-census.tarql ../catalog/census_dataset.csv > ../output/census_output.ttl
