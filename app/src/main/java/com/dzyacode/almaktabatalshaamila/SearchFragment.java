package com.dzyacode.almaktabatalshaamila;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.folioreader.FolioReader;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.domain.TableOfContents;
import nl.siegmann.epublib.epub.EpubReader;


public class SearchFragment extends Fragment {

    public SearchFragment() {
    }

    EditText searchText;
    Button searchBtn;
    ListView searchResults;

    File direc;
    FolioReader folioReader;

    // Declare searchResultsList outside of any method
    private List<SearchResult> searchResultsList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_search, container, false);

        searchText = root.findViewById(R.id.search_textView);
        searchBtn = root.findViewById(R.id.search_btn);
        searchResults = root.findViewById(R.id.searchResultsListView);

        // Hide the search button initially
        searchBtn.setVisibility(View.INVISIBLE);

        // Show the search button when text is entered into the search box
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchBtn.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // 1. Create a list of file objects that represent the files in the directory.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            direc = new File(Environment.DIRECTORY_DOWNLOADS, "/elshamela/");
        } else {
            direc = new File(Environment.getExternalStorageDirectory() + "/elshamela/");
        }

        searchBtn.setOnClickListener(v -> {
            // Clear searchResultsList before populating it with new results
            searchResultsList.clear();

            String searchTerm = searchText.getText().toString().trim(); // Get search term from searchText and trim any leading/trailing whitespace
            List<File> epubFiles = getEpubFiles(String.valueOf(direc));

            Log.d("TAGGGGGG:",String.valueOf(epubFiles.size()));

            for (File epubFile : epubFiles) {
                try {
                    Book book = new EpubReader().readEpub(new FileInputStream(epubFile));

                    Log.d("TAGBOOK:", String.valueOf(book.getContents().size()));

                    byte[] bText = book.getContents().get(1).getData();

                    String bookText = new String(bText);

                    Log.d("BOOK OPENED: ", bookText);

                    // Use the search term as the regular expression pattern
                    String pattern = searchTerm;
                    Pattern regex = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
                    Matcher matcher = regex.matcher(bookText);

                    while (matcher.find()) {
                        int start = matcher.start();
                        int end = matcher.end();
                        String bookTex = new String(bText);
                        int sentenceStart = bookTex.lastIndexOf(".", start) + 1;
                        int sentenceEnd = bookTex.indexOf(".", end);
                        if (sentenceEnd >= 0) {
                            String sentence = bookTex.substring(sentenceStart, sentenceEnd);

                            // Get the Resource that contains the text
                            Resource resource = book.getSpine().getResource(start);

                            // Get the TableOfContents of the book
                            TableOfContents tableOfContents = book.getTableOfContents();

                            // Find the location of the Resource in the book
                            int location = tableOfContents.getAllUniqueResources().indexOf(resource.getHref());

                            // Get the page number that contains the text
                            //int pageNumber = tableOfContents.getTocReferences().get(location).getPageNumber() + 1;

                            SearchResult result = new SearchResult(epubFile.getName(),  50, sentence);
                            searchResultsList.add(result);
                        }
                    }


                } catch (Exception e) {

                    Log.d("FILE ERROR",e.getMessage());
                    e.printStackTrace();
                }
            }

            // Set up the ListView adapter with the search results
            SearchResultsAdapter adapter = new SearchResultsAdapter(getActivity(), searchResultsList);
            searchResults.setAdapter(adapter);
        });



        return root;
    }

    private class SearchResultsAdapter extends ArrayAdapter<SearchResult> {
        private final List<SearchResult> results;

        public SearchResultsAdapter(Context context, List<SearchResult> results) {
            super(context, 0, results);
            this.results = results;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
            }
            TextView textView = convertView.findViewById(android.R.id.text1);
            SearchResult result = results.get(position);
            textView.setText(result.toString());
            return convertView;
        }
    }


    private static List<File> getEpubFiles(String directoryPath) {
        File directory = new File(directoryPath);
        File[] files = directory.listFiles();
        List<File> epubFiles = new ArrayList<>();

        for (File file : files) {
            if (file.getName().endsWith(".epub")) {
                epubFiles.add(file);
            }
        }

        return epubFiles;
    }

    public class SearchResult {
        private final String bookName;
        private final int page;
        private final String sentence;

        public SearchResult(String bookName, int page, String sentence) {
            this.bookName = bookName;
            this.page = page;
            this.sentence = sentence;
        }

        public String getBookName() {
            return bookName;
        }

        public int getPage() {
            return page;
        }

        public String getSentence() {
            return sentence;
        }

        @Override
        public String toString() {
            return String.format("%s (p. %d): %s", bookName, page, sentence);
        }
    }

}
