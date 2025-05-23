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
    fun saveLicenseCode(code: String)
    fun getLicenseCode(): String?
    fun hasLicenseCode(): Boolean

    // Active module methods
    fun saveActiveModule(route: String)
    fun getActiveModule(): String?
    fun clearActiveModule()

    // Menu cache methods
    fun saveMenuCache(menuJson: String)
    fun getMenuCache(): String?
    fun getMenuCacheTimestamp(): Long
    fun clearMenuCache()
}

class UserPreferencesImpl(context: Context) : UserPreferences {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("portal_digital_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_AUTH_TOKEN = "auth_token"
        private const val KEY_LICENSE_CODE = "license_code"
        private const val KEY_ACTIVE_MODULE = "active_module"
        private const val KEY_MENU_CACHE = "menu_cache"
        private const val KEY_MENU_CACHE_TIMESTAMP = "menu_cache_timestamp"
        private const val MENU_CACHE_EXPIRY_HOURS = 24L // Cache expiry in hours
    }

    override fun saveAuthToken(token: String) {
        sharedPreferences.edit { putString(KEY_AUTH_TOKEN, token) }
    }

    override fun getAuthToken(): String? {
        return sharedPreferences.getString(KEY_AUTH_TOKEN, null)
    }

    override fun clearAuthToken() {
        sharedPreferences.edit { remove(KEY_AUTH_TOKEN) }
    }

    override fun isLoggedIn(): Boolean {
        return getAuthToken() != null
    }

    override fun saveLicenseCode(code: String) {
        sharedPreferences.edit { putString(KEY_LICENSE_CODE, code) }
    }

    override fun getLicenseCode(): String? {
        return sharedPreferences.getString(KEY_LICENSE_CODE, null)
    }

    override fun hasLicenseCode(): Boolean {
        return getLicenseCode() != null && getLicenseCode()!!.isNotEmpty()
    }

    override fun saveActiveModule(route: String) {
        sharedPreferences.edit { putString(KEY_ACTIVE_MODULE, route) }
    }

    override fun getActiveModule(): String? {
        return sharedPreferences.getString(KEY_ACTIVE_MODULE, null)
    }

    override fun clearActiveModule() {
        sharedPreferences.edit { remove(KEY_ACTIVE_MODULE) }
    }

    // Menu cache implementations
    override fun saveMenuCache(menuJson: String) {
        sharedPreferences.edit {
            putString(KEY_MENU_CACHE, menuJson)
            putLong(KEY_MENU_CACHE_TIMESTAMP, System.currentTimeMillis())
        }
    }

    override fun getMenuCache(): String? {
        return sharedPreferences.getString(KEY_MENU_CACHE, null)
    }

    override fun getMenuCacheTimestamp(): Long {
        return sharedPreferences.getLong(KEY_MENU_CACHE_TIMESTAMP, 0)
    }

    override fun clearMenuCache() {
        sharedPreferences.edit {
            remove(KEY_MENU_CACHE)
            remove(KEY_MENU_CACHE_TIMESTAMP)
        }
    }
}