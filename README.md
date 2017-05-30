# Media Finder (Ksubaka Tech Test)

## Online API's Used
1. Last FM (Music) - http://www.last.fm/api
2. The Movie Database (Movies) - https://www.themoviedb.org/documentation/api

## Setup
1. After cloning repo, open terminal and cd into project directory e.g (cd /projects/mediafinder)
2. run "mvn clean install" command
3. cd into generated target folder e.g (cd target/)
4. run program jar e.g (java -jar mediafinder-0.0.1-SNAPSHOT.jar -Dapi="tmdb" -Dquery="district 9")

## Types of Queries

The Media Finder App, allows you to perform queries against two different API's stated above.

1. For movies you would use the following arguments (-Dapi=tmdb -Dquery="Star Wars")
2. For a music album search, you would use following arguments (-Dapi=lastfm -Dquery="Deftones")
