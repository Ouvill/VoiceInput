package  net.ouvill.voiceinput;

import java.util.ArrayList;
import java.util.List;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.Vibrator;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import org.w3c.dom.Text;


public class MainActivity extends AppCompatActivity {
    private static int MY_PERMISSIONS_REQUEST_RECORD_AUDIO = 100;
    private TextView textView;

    SpeechRecognizer mSpeechRec;
    SampleRecognitionListener mRecogListener = new SampleRecognitionListener();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.text_view);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {

            // permissionが許可されていません
            if (shouldShowRequestPermissionRationale(Manifest.permission.RECORD_AUDIO)) {
                // 許可ダイアログで今後表示しないにチェックされていない場合
            }

            // permissionを許可してほしい理由の表示など

            // 許可ダイアログの表示
            // MY_PERMISSIONS_REQUEST_READ_CONTACTSはアプリ内で独自定義したrequestCodeの値
            requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO},
                MY_PERMISSIONS_REQUEST_RECORD_AUDIO
                );

            return;
        }


        // 音声認識開始
        mSpeechRec = SpeechRecognizer.createSpeechRecognizer(getApplicationContext());
        mSpeechRec.setRecognitionListener(mRecogListener);
        mSpeechRec.startListening(RecognizerIntent
            .getVoiceDetailsIntent(getApplicationContext()));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_RECORD_AUDIO) {
            // 先ほどの独自定義したrequestCodeの結果確認

                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // ユーザーが許可したとき
                    // 許可が必要な機能を改めて実行する
                } else {
                    // ユーザーが許可しなかったとき
                    // 許可されなかったため機能が実行できないことを表示する
                }
                return;

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mSpeechRec.destroy();
    }

    public class SampleRecognitionListener implements RecognitionListener {

        @Override
        public void onResults(Bundle results) {
            // 音声検索結果
            ArrayList<String> reslurtWordList = results
                .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

            // 候補の一つ目を表示
            String resultWord = reslurtWordList.get(0);
            Toast.makeText(getApplicationContext(), resultWord,
                Toast.LENGTH_SHORT).show();

            // 再度音声認識開始
            mSpeechRec.setRecognitionListener(mRecogListener);
            mSpeechRec.startListening(RecognizerIntent
                .getVoiceDetailsIntent(getApplicationContext()));

        }

        @Override
        public void onPartialResults(Bundle partialResults) {
            List<String> list = (List) partialResults.get(SpeechRecognizer.RESULTS_RECOGNITION);
            Log.v(SampleRecognitionListener.this.getClass().getSimpleName(), "onPartialResults");
            for (String str : list) {
                Log.v(SampleRecognitionListener.this.getClass().getSimpleName(), str);
            }

        }

        @Override
        public void onEvent(int eventType, Bundle params) {

        }

        @Override
        public void onReadyForSpeech(Bundle params) {

        }

        @Override
        public void onBeginningOfSpeech() {

        }

        @Override
        public void onRmsChanged(float rmsdB) {

        }

        @Override
        public void onBufferReceived(byte[] buffer) {

        }

        @Override
        public void onEndOfSpeech() {

        }

        @Override
        public void onError(int error) {
            // 再度音声認識開始　※候補が見つからない時とかにここが呼ばれるっぽい
            mSpeechRec.setRecognitionListener(mRecogListener);
            mSpeechRec.startListening(RecognizerIntent
                .getVoiceDetailsIntent(getApplicationContext()));
        }


    }
}
