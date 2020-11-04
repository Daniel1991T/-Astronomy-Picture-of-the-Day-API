# Astronomy Picture of the Day API
  I created a small application in which I took data from the NASA API APOD server (One of the most popular websites at NASA is the Astronomy Picture of the Day. In fact, this website is one of the most popular websites across all federal agencies. It has the popular appeal of a Justin Bieber video.This endpoint structures the APOD imagery and associated metadata so that it can be repurposed for other applications.In addition, if the concept_tags parameter is set to True, then keywords derived from the image explanation are These keywords could be used as auto-generated hashtags for twitter or instagram feeds; but generally help with discoverability of relevant imagery.)
  I use kotlin at programming language, desing pattern as Singleton, Model-View-ViewModel, dependecy injection using Dagger Hilt and JetPack (lifecycle-viewmodel), and some external library as Retrofit for to retrieve data from the server and to create the bridge between the application and the server, moshi to convert json data received from the server to my class NasaPhoto. The application will display on your screen every day the most popular photo on the NASA website.
