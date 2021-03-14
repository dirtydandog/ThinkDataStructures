package com.allendowney.thinkdast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

public class WikiPhilosophy {

    final static List<String> visited = new ArrayList<String>();
    final static WikiFetcher wf = new WikiFetcher();
    String url;


    /**
     * Tests a conjecture about Wikipedia and Philosophy.
     * <p>
     * https://en.wikipedia.org/wiki/Wikipedia:Getting_to_Philosophy
     * <p>
     * 1. Clicking on the first non-parenthesized, non-italicized link
     * 2. Ignoring external links, links to the current page, or red links
     * 3. Stopping when reaching "Philosophy", a page with no links or a page
     * that does not exist, or when a loop occurs
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
//        String destination = "https://en.wikipedia.org/wiki/Philosophy";
        String source = "https://en.wikipedia.org/wiki/Java_(programming_language)";
        String destination = "https://en.wikipedia.org/wiki/Formal_language";

        testConjecture(destination, source, 10);
    }

    /**
     * Starts from given URL and follows first link until it finds the destination or exceeds the limit.
     *
     * @param destination
     * @param source
     * @throws IOException
     */
    public static void testConjecture(String destination, String source, int limit) throws IOException {
        // TODO: FILL THIS IN!
        String start = source;
        for (int i = 0; i < limit; i += 1) {
            // wikifetcher wf parses source into Elements
            Elements paragraphs = wf.fetchWikipedia(start);

            // Parse the paragraphs with WikiParser
            WikiParser wp = new WikiParser(paragraphs);

            Element firstLink = wp.findFirstLink();
            String url = firstLink.attr("abs:href");
            visited.add(url);
            System.out.println(firstLink.text());
            start = url;
        }
    }
}
