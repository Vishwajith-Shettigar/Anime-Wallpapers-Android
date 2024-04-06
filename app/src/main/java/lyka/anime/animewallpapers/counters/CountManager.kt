package lyka.anime.animewallpapers.counters

import android.content.Context
import android.content.SharedPreferences



val PREF_NAME="FEEDBACK"
val FEEDBACK_STATE="FEEDBACK_STATE"
class CountManager private constructor(
  private val context: Context
) {
  private var count = 0
  private  val preferences: SharedPreferences by lazy {
    context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
  }

  fun disableFeedback(){
    preferences.edit().putBoolean(FEEDBACK_STATE,false).apply()
  }

  fun getFeedbackState():Boolean{
   return preferences.getBoolean(FEEDBACK_STATE,true)
  }

  companion object {
    @Volatile
    private var instance: CountManager? = null

    @Synchronized
    fun getInstance(context: Context): CountManager {
      if (instance == null) {
        instance = CountManager(context)
      }
      return instance as CountManager
    }
  }

  fun getCount(): Int {
    return count
  }

  fun incrementCount() {
    count++
  }
}


