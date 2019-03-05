package ru.tinkoff.daria.test.restdataprovider.model;

import java.util.List;

public class GetPersonsResponse {

    private List<Person> results;
    private Info info;

    public List<Person> getResults() {
        return results;
    }

    public void setResults(List<Person> results) {
        this.results = results;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public static class Info {

        private String seed;
        private int results;
        private int page;
        private String version;

        public String getSeed() {
            return seed;
        }

        public void setSeed(String seed) {
            this.seed = seed;
        }

        public int getResults() {
            return results;
        }

        public void setResults(int results) {
            this.results = results;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }
    }
}