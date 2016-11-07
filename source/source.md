[//]: # (This text is written in mark down markup language. You can read it on https://github.com/janprasil/mi-swe-semestral-work.)

# mi-swe-semestral-work

The goal of this semestral work is to link open data from Brexit referendum, United Kingdom prosperity index data and Census 2011 results together and try to determine what was the dependency for the acceptation of the leaving the European Union.

## Used Data

### First Dataset

* Name: EU referendum results
* Dataset Source: http://www.electoralcommission.org.uk/find-information-by-subject/elections-and-referendums/past-elections-and-referendums/eu-referendum/electorate-and-count-information
* Format: csv
* Role: Referendum results
* Triplifier: tarql

### Second Dataset

* Name: THE UK PROSPERITY INDEX
* Dataset Source: [http://uk.prosperity.com](http://uk.prosperity.com/docs/2016/PressReleaseDataset.xlsx)
* Format: xls
* Processing: export to csv
* Roles: Index of prosperity in different areas
* Triplifier: export to csv and tarql

### Third Dataset

* Name: Gibbs/uk-postcodes
* Dataset Source: https://github.com/Gibbs/UK-Postcodes/blob/master/postcodes.csv
* Format: csv
* Roles: Determine if the area location depends on results
* Triplifier: tarql

### Fourth Dataset

* Name: General health by religion by sex by age
* Dataset Source: http://www.nomisweb.co.uk/census/2011/lc3203ew
* Format: csv
* Roles: UK towns statistics - age, health, religion and sex
* Triplifier: tarql

## UML Data Diagram

