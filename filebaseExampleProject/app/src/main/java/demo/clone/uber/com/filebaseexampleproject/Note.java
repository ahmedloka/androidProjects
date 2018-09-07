package demo.clone.uber.com.filebaseexampleproject;

import com.google.firebase.firestore.Exclude;

public class Note {

    private String mTitle  , mDescription , mDocumentID ;
    private int mPriority ;

    public Note() {
    }

    public Note(String mTitle, String mDescription , int mPriority) {
        this.mTitle = mTitle;
        this.mDescription = mDescription;
        this.mPriority = mPriority ;
    }

    @Exclude
    public String getmDocumentID() {
        return mDocumentID;
    }

    public void setmDocumentID(String mDocumentID) {
        this.mDocumentID = mDocumentID;
    }

    public int getmPriority() {
        return mPriority;
    }

    public void setmPriority(int mPriority) {
        this.mPriority = mPriority;
    }


    public String getmTitle() {
        return mTitle;
    }

    public String getmDescription() {
        return mDescription;
    }
}
