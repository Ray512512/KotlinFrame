package com.ray.frame.presentation.kotlinx.extensions.krealmextensions

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposables
import io.realm.*


/**
 * Query for all items and listen to changes returning an Single.
 */
fun <T : RealmModel> T.queryAllAsSingle() = performQuery()

/**
 * Queries for entities in database asynchronously, and observe changes returning an Single.
 */
fun <T : RealmModel> T.queryAsSingle(query: Query<T>) = performQuery(query = query)

/**
 * Query for sorted entities and observe changes returning a Single.
 */
fun <T : RealmModel> T.querySortedAsSingle(fieldName : List<String>, order : List<Sort>, query: Query<T>? = null) = performQuery(fieldName, order, query)

/**
 * Query for sorted entities and observe changes returning a Single.
 */
fun <T : RealmModel> T.querySortedAsSingle(fieldName : String, order : Sort, query: Query<T>? = null) = performQuery(listOf(fieldName), listOf(order), query)

private fun <T : RealmModel> T.performQuery(fieldName : List<String>? = null, order : List<Sort>? = null, query: Query<T>? = null): Single<List<T>> {
    val looper = getLooper()
    return Single.create<List<T>>({ emitter ->

        val realm = com.ray.frame.presentation.kotlinx.extensions.krealmextensions.RealmConfigStore.Companion.fetchConfiguration(javaClass)?.realm() ?: Realm.getDefaultInstance()
        val realmQuery: RealmQuery<T> = realm.where(this.javaClass)
        query?.invoke(realmQuery)

        val result = if(fieldName != null && order != null ) {
            realmQuery.findAllSortedAsync(fieldName.toTypedArray(), order.toTypedArray())
        }
        else {
            realmQuery.findAllAsync()
        }

        result.addChangeListener { it ->
            emitter.onSuccess(realm.copyFromRealm(it))
        }

        emitter.setDisposable(Disposables.fromAction {
            result.removeAllChangeListeners()
            realm.close()
            if (android.os.Looper.getMainLooper() != looper) {
                looper?.thread?.interrupt()
            }
        })
    }).subscribeOn(AndroidSchedulers.from(looper))
            .unsubscribeOn(AndroidSchedulers.from(looper))
}