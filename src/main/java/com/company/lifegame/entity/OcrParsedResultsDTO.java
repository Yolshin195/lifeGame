package com.company.lifegame.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class OcrParsedResultsDTO implements Serializable {

    @JsonProperty("ParsedResults")
    private List<ParsedResult> parsedResults;

    @JsonProperty("OCRExitCode")
    private int OCRExitCode;

    @JsonProperty("IsErroredOnProcessing")
    private boolean IsErroredOnProcessing;

    @JsonProperty("ProcessingTimeInMilliseconds")
    private String ProcessingTimeInMilliseconds;

    @JsonProperty("SearchablePDFURL")
    private String SearchablePDFURL;

    public record ParsedResult(
            @JsonProperty("TextOverlay")
            TextOverlay TextOverlay,
            @JsonProperty("TextOrientation")
            String TextOrientation,
            @JsonProperty("FileParseExitCode")
            int FileParseExitCode,
            @JsonProperty("ParsedText")
            String ParsedText,
            @JsonProperty("ErrorMessage")
            String ErrorMessage,
            @JsonProperty("ErrorDetails")
            String ErrorDetails) {

        public record TextOverlay(
                @JsonProperty("Lines")
                List<Line> Lines,
                @JsonProperty("HasOverlay")
                boolean HasOverlay,
                @JsonProperty("Message")
                String Message) {

            public record Line(
                    @JsonProperty("LineText")
                    String LineText,
                    @JsonProperty("Words")
                    List<Word> Words,
                    @JsonProperty("MaxHeight")
                    double MaxHeight,
                    @JsonProperty("MinTop")
                    double MinTop) {

                public record Word(
                        @JsonProperty("WordText")
                        String WordText,
                        @JsonProperty("Left")
                        double Left,
                        @JsonProperty("Top")
                        double Top,
                        @JsonProperty("Height")
                        double Height,
                        @JsonProperty("Width")
                        double Width) {
                }
            }
        }
    }

    public List<ParsedResult> getParsedResults() {
        return parsedResults;
    }

    public void setParsedResults(List<ParsedResult> parsedResults) {
        this.parsedResults = parsedResults;
    }

    public int getOCRExitCode() {
        return OCRExitCode;
    }

    public void setOCRExitCode(int OCRExitCode) {
        this.OCRExitCode = OCRExitCode;
    }

    public boolean isErroredOnProcessing() {
        return IsErroredOnProcessing;
    }

    public void setErroredOnProcessing(boolean erroredOnProcessing) {
        IsErroredOnProcessing = erroredOnProcessing;
    }

    public String getProcessingTimeInMilliseconds() {
        return ProcessingTimeInMilliseconds;
    }

    public void setProcessingTimeInMilliseconds(String processingTimeInMilliseconds) {
        ProcessingTimeInMilliseconds = processingTimeInMilliseconds;
    }

    public String getSearchablePDFURL() {
        return SearchablePDFURL;
    }

    public void setSearchablePDFURL(String searchablePDFURL) {
        SearchablePDFURL = searchablePDFURL;
    }
}
