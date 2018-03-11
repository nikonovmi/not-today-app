package com.oohdev.oohreminder.core.api;

import android.support.annotation.NonNull;

import com.oohdev.oohreminder.core.BookDataObject;
import com.oohdev.oohreminder.core.model.OpenLibSearch;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookApiHelper {

    public static void getBookDataObj(@NonNull String title, @NonNull String defAuthor, @NonNull final ResultHandler resultHandler) {
        final BookDataObject result = new BookDataObject(title, defAuthor);
        OpenLibSearchInterface searchInterface = OpenLibSearchAPIClient.getRetrofitInstance().create(OpenLibSearchInterface.class);
        Call<OpenLibSearch> call = searchInterface.findBooks(title);
        call.enqueue(new Callback<OpenLibSearch>() {
            @Override
            public void onResponse(@NonNull Call<OpenLibSearch> call, @NonNull Response<OpenLibSearch> response) {
                if (response.body() != null && response.body().results.size() > 0) {
                    OpenLibSearch.Entry entry = response.body().results.get(0);
                    if (entry.authors.size() > 0) {
                        result.setAuthor(entry.authors.get(0));
                    }
                    if (entry.coverId != null) {
                        result.setCoverUrl(getCoverUrlFromCoverId(entry.coverId));
                    }
                }
                resultHandler.onResult(result, false);
            }

            @Override
            public void onFailure(@NonNull Call<OpenLibSearch> call, @NonNull Throwable t) {
                call.cancel();
                resultHandler.onResult(result, true);
            }
        });
    }

    @NonNull
    private static String getCoverUrlFromCoverId(int coverId) {
        return OpenLibSearchAPIClient.COVER_URL_PREFIX + Integer.toString(coverId) + OpenLibSearchAPIClient.COVER_URL_SUFFIX;
    }

    public interface ResultHandler {
        void onResult(@NonNull BookDataObject bookDataObject, boolean isFailed);
    }
}
