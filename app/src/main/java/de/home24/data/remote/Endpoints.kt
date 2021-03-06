package de.home24.data.remote

import de.home24.BuildConfig.API_VER
import de.home24.BuildConfig.BASE_URL

class Endpoints {

    companion object {
        const val SERVER_URL = BASE_URL + API_VER

        const val PARAM_APP_DOMAIN = "1"
        const val PARAM_LOCALE = "de_DE"//Locale

        //DOMAIN_ARTICLES_DATA
        const val PARAM_CATEGORIES = "100"//category number
        const val PARAM_ARTICLES_LIMIT = "10"//article list size to be fetched

        const val DOMAIN_ARTICLES_DATA = "categories/" + PARAM_CATEGORIES + "/articles?appDomain=" +
                PARAM_APP_DOMAIN + "&locale=" + PARAM_LOCALE + "&limit=" + PARAM_ARTICLES_LIMIT
    }
}