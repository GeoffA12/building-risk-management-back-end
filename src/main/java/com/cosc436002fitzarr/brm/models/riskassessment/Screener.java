package com.cosc436002fitzarr.brm.models.riskassessment;

import com.cosc436002fitzarr.brm.enums.Response;

import java.util.Objects;

public class Screener {
    private String question;
    private Response response;

    public Screener(String question, Response response) {
        this.question = question;
        this.response = response;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Screener screener = (Screener) o;
        return getQuestion().equals(screener.getQuestion()) &&
                getResponse() == screener.getResponse();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getQuestion(), getResponse());
    }

    @Override
    public String toString() {
        return "Screener{" +
                "question='" + question + '\'' +
                ", response=" + response +
                '}';
    }
}
