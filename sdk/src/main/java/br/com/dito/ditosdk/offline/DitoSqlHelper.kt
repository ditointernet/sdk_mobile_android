package br.com.dito.ditosdk.offline

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase


@Entity(tableName = "identify")
data class IdentifyOffline(
    @PrimaryKey val _id: String,
    val json: String?,
    val reference: String?,
    val send: Boolean,
)

@Dao
interface IdentifyOfflineDao {
    @Query("SELECT * FROM identify")
    fun getAll(): List<IdentifyOffline>

    @Query("SELECT * FROM identify LIMIT 1")
    fun getFirst(): IdentifyOffline

    @Insert
    fun insert(vararg identify: IdentifyOffline)

    @Query("UPDATE identify SET send=:send  WHERE _id =:id")
    fun update(send: Boolean, id: Int)

    @Query("DELETE FROM identify WHERE _id =:id")
    fun delete(id: Int)
}


@Entity(tableName = "event")
data class EventOffline(
    @PrimaryKey/*(autoGenerate = true)*/ val _id: Int? = null,
    val json: String?,
    val retry: Int
)

@Dao
interface EventOfflineDao {
    @Query("SELECT * FROM event")
    fun getAll(): List<EventOffline>

    @Insert
    fun insert(vararg event: EventOffline)

    @Query("UPDATE event SET retry=:retry  WHERE _id =:id")
    fun update(id: Int, retry: Int)

    @Query("DELETE FROM identify WHERE _id =:id")
    fun delete(id: Int)
}


@Entity(tableName = "notification")
data class NotificationOffline(
    @PrimaryKey/*(autoGenerate = true)*/ val _id: Int? = null,
    val json: String?,
    val retry: Int
)

@Dao
interface NotificationOfflineDao {
    @Query("SELECT * FROM notification")
    fun getAll(): List<NotificationOffline>

    @Insert
    fun insert(vararg notification: NotificationOffline)

    @Query("UPDATE event SET retry=:retry  WHERE _id =:id")
    fun update(id: Int, retry: Int)

    @Query("DELETE FROM identify WHERE _id =:id")
    fun delete(id: Int)
}


@Database(
    entities = [IdentifyOffline::class, EventOffline::class, NotificationOffline::class],
    version = 1
)
abstract class DitoSqlHelper() : RoomDatabase() {
    abstract fun identifyDao(): IdentifyOfflineDao
    abstract fun eventDao(): EventOfflineDao
    abstract fun notificationDao(): NotificationOfflineDao
}

