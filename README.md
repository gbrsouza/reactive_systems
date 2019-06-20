# Reactive Systems

## Files

- akka-quickstart-java : A simple example to use Akka
- reducemap : A simple reduce map using akka
- reactive-TFidF : A implementation of the TF-iDF algorithm using Akka

## Reactive TF-idF

    Tf-idf stands for term frequency-inverse document frequency, and the tf-idf weight is a weight often used in information retrieval and text mining. This weight is a statistical measure used to evaluate how important a word is to a document in a collection or corpus. The importance increases proportionally to the number of times a word appears in the document but is offset by the frequency of the word in the corpus. Variations of the tf-idf weighting scheme are often used by search engines as a central tool in scoring and ranking a document's relevance given a user query [http://www.tfidf.com/](reference).

  The project use the Akka to build a parrallel TF-idF calculation for a set of files.



### How to use

1. Import the project to a IDE java with Maven project
2. access **App.java** in **bigdata.reactive** and run as Java project
3. The application use a default dataset of files
