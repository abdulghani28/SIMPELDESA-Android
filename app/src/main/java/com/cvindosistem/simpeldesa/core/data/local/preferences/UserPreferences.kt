package com.cvindosistem.simpeldesa.core.data.local.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

interface UserPreferences {
    fun saveAuthToken(token: String)
    fun getAuthToken(): String?
    fun clearAuthToken()
    fun isLoggedIn(): Boolean

    // License code methods
//    fun saveLicenseCode(code: String)
//    fun getLicenseCode(): String?
//    fun hasLicenseCode(): Boolean

    fun clearAllUserData()

    // Menu cache methods
    fun saveMenuCache(menuJson: String)
    fun getMenuCache(): String?
    fun getMenuCacheTimestamp(): Long
    fun clearMenuCache()
}

/**
 * Implementation of [UserPreferences] using Android's SharedPreferences to persist user data.
 *
 * This class handles storage and retrieval of:
 * - Authentication token
 * - License code
 * - Cached menu data (with timestamp for expiry)
 * - Login status checking
 */
class UserPreferencesImpl(context: Context) : UserPreferences {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("portal_digital_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_AUTH_TOKEN = "auth_token"
//        private const val KEY_LICENSE_CODE = "license_code"
        private const val KEY_MENU_CACHE = "menu_cache"
        private const val KEY_MENU_CACHE_TIMESTAMP = "menu_cache_timestamp"
        private const val MENU_CACHE_EXPIRY_HOURS = 24L // Cache expiry in hours
    }

    /**
     * Saves the authentication token to SharedPreferences.
     */
    override fun saveAuthToken(token: String) {
        sharedPreferences.edit { putString(KEY_AUTH_TOKEN, token) }
    }

    /**
     * Retrieves the authentication token from SharedPreferences.
     */
    override fun getAuthToken(): String? {
        return sharedPreferences.getString(KEY_AUTH_TOKEN, null)
    }

    /**
     * Clears the authentication token from SharedPreferences.
     */
    override fun clearAuthToken() {
        sharedPreferences.edit { remove(KEY_AUTH_TOKEN) }
    }

    /**
     * Returns true if an authentication token exists (i.e., user is logged in).
     */
    override fun isLoggedIn(): Boolean {
        return getAuthToken() != null
    }

    /**
     * Saves the license code (typically used for device/instance registration).
     */
//    override fun saveLicenseCode(code: String) {
//        sharedPreferences.edit { putString(KEY_LICENSE_CODE, code) }
//    }

    /**
     * Retrieves the license code.
     */
//    override fun getLicenseCode(): String? {
//        return sharedPreferences.getString(KEY_LICENSE_CODE, null)
//    }

    /**
     * Checks whether a license code exists and is not empty.
     */
//    override fun hasLicenseCode(): Boolean {
//        return getLicenseCode() != null && getLicenseCode()!!.isNotEmpty()
//    }

    // ----------------------
    // Menu cache methods
    // ----------------------

    /**
     * Saves the menu JSON and a timestamp to indicate when it was cached.
     */
    override fun saveMenuCache(menuJson: String) {
        sharedPreferences.edit {
            putString(KEY_MENU_CACHE, menuJson)
            putLong(KEY_MENU_CACHE_TIMESTAMP, System.currentTimeMillis())
        }
    }

    /**
     * Retrieves the cached menu JSON (if exists).
     */
    override fun getMenuCache(): String? {
        return sharedPreferences.getString(KEY_MENU_CACHE, null)
    }

    /**
     * Retrieves the timestamp of when the menu cache was last saved.
     */
    override fun getMenuCacheTimestamp(): Long {
        return sharedPreferences.getLong(KEY_MENU_CACHE_TIMESTAMP, 0)
    }

    /**
     * Clears the cached menu data and its timestamp.
     */
    override fun clearMenuCache() {
        sharedPreferences.edit {
            remove(KEY_MENU_CACHE)
            remove(KEY_MENU_CACHE_TIMESTAMP)
        }
    }

    /**
     * Clears all user-related data from SharedPreferences, typically called on logout.
     */
    override fun clearAllUserData() {
        sharedPreferences.edit {
            remove(KEY_AUTH_TOKEN)
            remove(KEY_MENU_CACHE)
            remove(KEY_MENU_CACHE_TIMESTAMP)
        }
    }
}