package com.example.finalnativas.utils;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OMDBApiResponse {
    @SerializedName("Search")
    private List<SearchItem> search;

    // otros atributos y métodos existentes

    public List<SearchItem> getSearch() {
        return search;
    }

    public static class SearchItem {
        @SerializedName("Title")
        private String title;

        @SerializedName("Year")
        private String year;

        @SerializedName("imdbID")
        private String imdbId;

        @SerializedName("Type")
        private String type;

        @SerializedName("Poster")
        private String poster;

        // getters y setters para los atributos

        public String getTitle() {
            return title;
        }

        public String getYear() {
            return year;
        }

        public String getImdbId() {
            return imdbId;
        }

        public String getType() {
            return type;
        }

        public String getPoster() {
            return poster;
        }
    }
}
