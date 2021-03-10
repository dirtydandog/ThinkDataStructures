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
     *
     * https://en.wikipedia.org/wiki/Wikipedia:Getting_to_Philosophy
     *
     * 1. Clicking on the first non-parenthesized, non-italicized link
     * 2. Ignoring external links, links to the current page, or red links
     * 3. Stopping when reaching "Philosophy", a page with no links or a page
     *    that does not exist, or when a loop occurs
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
//        String destination = "https://en.wikipedia.org/wiki/Philosophy";
        String source = "https://en.wikipedia.org/wiki/Java_(programming_language)";
        String destination = "https://en.wikipedia.org/wiki/General-purpose_language";


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
        // download and parse the document
//        while (!destination.equals(source))
        String url = source;

        if (limit == 0) {
            return;
        }

        Connection conn = Jsoup.connect(source);
        Document doc = conn.get();

        // select the content text and pull out the paragraphs.
        Element content = doc.getElementById("mw-content-text");

        // TODO: avoid selecting paragraphs from sidebars and boxouts
        Elements paras = content.select("p");
        Element firstPara = paras.get(1);

        Iterable<Node> iter = new WikiNodeIterable(firstPara);
        for (Node node: iter) {
            if (node.hasAttr("href")) {
                url = node.attr("abs:href");
                System.out.println(url);
                visited.add(url);
                if (url.equals(destination)) {
                    System.out.println("Success");
                    System.out.println(visited);
                    return;
                }
                System.out.println(limit);
                testConjecture(destination, url, limit - 1);
            }
            testConjecture(destination, url, limit - 1);

        }
    }
}
