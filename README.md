# PropertyAnalytics
Analysing the UK property data

- You will need a Java IDE (I use IntelliJ) and Maven to open the project
- You will have to create a folder which will contain all the raw data you will need going forward
- Search the project for every instances of the "[DATA_PATH]" string and replace those with the path to the data folder you created

- Download the latest price paid data from the UK gov: http://prod.publicdata.landregistry.gov.uk.s3-website-eu-west-1.amazonaws.com/pp-complete.csv
- Download the latest post-code longitude/latitude data: https://www.freemaptools.com/download/outcode-postcodes/postcode-outcodes.csv
- Save those into the data folder

- You will now need to run the sorter code: analysis.property.SortFile to create a chronological time sorted version of the price paid data
- Once done you can run the actual code to create the location-based price growth output CSV: analysis.property.PropertyAnalysis
- You need a bit of RAM for this step - around 5g should do the trick

- Once the CSV file is created you can now open the Property python notepad to generate the animation of the charts
