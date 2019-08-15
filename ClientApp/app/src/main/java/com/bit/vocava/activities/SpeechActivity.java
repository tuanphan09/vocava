/*
 * Copyright 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bit.vocava.activities;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bit.vocava.R;
import com.bit.vocava.speech.SpeakerVertification;
import com.bit.vocava.speech.SpeechService;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;


public class SpeechActivity extends BaseActivity {

    private static final String FRAGMENT_MESSAGE_DIALOG = "message_dialog";

    private static final String STATE_RESULTS = "results";

    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 1;



    private SpeechService mSpeechService;
    private SpeakerVertification speakerVertification = new SpeakerVertification();
    private static boolean isVertified = false;

    // View references
    private ResultAdapter mAdapter;
    private RecyclerView mRecyclerView;

    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder binder) {
            mSpeechService = SpeechService.from(binder);
            mSpeechService.addListener(mSpeechServiceListener);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mSpeechService = null;
        }

    };

    private String mPathAudio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech);

        final Resources resources = getResources();
        final Resources.Theme theme = getTheme();

        mPathAudio = getIntent().getStringExtra("pathAudio");

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        final ArrayList<String> results = savedInstanceState == null ? null :
                savedInstanceState.getStringArrayList(STATE_RESULTS);
        mAdapter = new ResultAdapter(results);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();

        // Prepare Cloud Speech API
        bindService(new Intent(this, SpeechService.class), mServiceConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {

        // Stop Cloud Speech API
        mSpeechService.removeListener(mSpeechServiceListener);
        unbindService(mServiceConnection);
        mSpeechService = null;

        super.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mAdapter != null) {
            outState.putStringArrayList(STATE_RESULTS, mAdapter.getResults());
        }
    }

    private final SpeechService.Listener mSpeechServiceListener =
            new SpeechService.Listener() {
                @Override
                public void onSpeechRecognized(final String text, final boolean isFinal) {
                    if (isFinal) {

                    }
                    if (!TextUtils.isEmpty(text)) {
                        runOnUiThread(() -> {
                            if (isFinal) {
                                Log.d("AppLog", text);
                                mAdapter.addResult(text);
                            } else {

                            }
                        });
                    }
                }

                @Override
                public void onSetupCompleted() {
                    try {
                        mSpeechService.recognizeInputStream(new FileInputStream(mPathAudio));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            };

    private static class ViewHolder extends RecyclerView.ViewHolder {

        TextView text;

        ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_result, parent, false));
            text = (TextView) itemView.findViewById(R.id.text);
        }

    }

    private static class ResultAdapter extends RecyclerView.Adapter<ViewHolder> {

        private final ArrayList<String> mResults = new ArrayList<>();

        ResultAdapter(ArrayList<String> results) {
            if (results != null) {
                mResults.addAll(results);
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if(isVertified){
                holder.text.setText(mResults.get(position));
                holder.text.setTextColor(Color.parseColor("#ffffff"));
                holder.text.setBackgroundColor(Color.parseColor("#1098F0"));

            }else{
                holder.text.setText(mResults.get(position));
            }

        }

        @Override
        public int getItemCount() {
            return mResults.size();
        }

        void addResult(String result) {
            mResults.add(mResults.size(), result);
            notifyItemInserted(mResults.size());
        }

        public ArrayList<String> getResults() {
            return mResults;
        }

    }

}
