package com.example.investmentguidevtb.data.source

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject

val Context.dataStore: DataStore<androidx.datastore.preferences.core.Preferences> by preferencesDataStore(name = "settings")

class UserSegmentationDataManager @Inject constructor(
    @ApplicationContext val context: Context
) {

    suspend fun getRisk(): Float {
        val riskPref = floatPreferencesKey("risk")
        val preferences = context.dataStore.data.first()
        return preferences[riskPref] ?: -1f
    }

    suspend fun setRisk(newValue: Float) {
        val riskPref = floatPreferencesKey("risk")
        context.dataStore.edit { settings ->
            settings[riskPref] = newValue
        }
    }

    suspend fun getDifficulty(): Float {
        val diffPref = floatPreferencesKey("difficulty")
        val preferences = context.dataStore.data.first()
        return preferences[diffPref] ?: -1f
    }

    suspend fun setDifficulty(newValue: Float) {
        val diffPref = floatPreferencesKey("difficulty")
        context.dataStore.edit { settings ->
            settings[diffPref] = newValue
        }
    }

    suspend fun getMainGoal(): String {
        val pref = stringPreferencesKey("mainGoal")
        val preferences = context.dataStore.data.first()
        return preferences[pref] ?: ""
    }

    suspend fun setMainGoal(newValue: String) {
        val pref = stringPreferencesKey("mainGoal")
        context.dataStore.edit { settings ->
            settings[pref] = newValue
        }
    }

    suspend fun getSegmentationPassed(): Boolean {
        val passedPref = booleanPreferencesKey("passed")
        val preferences = context.dataStore.data.first()
        return preferences[passedPref] ?: false
    }

    suspend fun setSegmentationPassed() {
        val passedPref = booleanPreferencesKey("passed")
        context.dataStore.edit { settings ->
            settings[passedPref] = true
        }
    }

}