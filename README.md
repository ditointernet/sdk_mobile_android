# Dito Android SDK

## Dependência

No build.gradle da diretório raiz do projeto você deve adicionar a dependência:

```
dependencies {
  implementation 'com.github.ditointernet:sdk_mobile_android:<LAST_VERSION>'
}
```

## Configuração

### AndroidManifest

No AndroidManifest é onde você configura as chaves de acesso da sua aplicação:

```
<application>
  ...
    <meta-data android:name="br.com.dito.API_KEY" android:value="<MY_API_KEY>"/>
    <meta-data android:name="br.com.dito.API_SECRET" android:value="<MY_APY_SCRET>"/>
 
</application>
```

#### Permissões

```
  <uses-permission android:name="android.permission.POST_NOTIFICATIONS" />
```

## Inicialização

No metodo onCreate da sua classe que estende de `Application` você deve iniciar o SDK:

```
override fun onCreate() {
    super.onCreate()
    val options = Options()
    options.debug = true
    Dito.init(this, options)
}
```

`Options` não é uma configuração obrigatório, e você pode usa-lo para as seguintes configurações:

* **retry** (default = 5) - É numero de tentativas que o SDK vai fazer de envio do evento.

* **iconNotification** - É o Ressource ID do icone para notificação

* **contentIntent** - É a Intent que será aberta quando o usuário clicar na notificação. Caso esse
  valor essa *null*, vamos tentar recuperar a Activity que está configurada
  como `<action android:name="android.intent.action.MAIN" />`.

## Envio de eventos

### Indentificar Usuário

Para identificar um usuário é preciso criar um objeto `Identify` passando no construtor um ID.

```
val identify = Identify("85496430259")
Dito.identify(identify)
```

A classe `Identify` tem os seguintes parâmetros:

* id - Identificador do usuário, obrigatório.
* name - Nome do usuário.
* email - E-mail do usuário
* gender - Orientação sexual do usuário
* location - Localização do usuário
* birthday - Data de nascimento do usuário
* data - É um objeto do tipo `CustomData` que aceita parâmetros customizados.

### Evento

```
Dito.track(Event("comprou", 2.5))
```

A classe `Event`tem os seguintes parâmetros:

* action - Nome do evento, obrigatório.
* revenue - Receita do evento.
* data - É um objeto do tipo `CustomData` que aceita parâmetros customizados.

## Push Notification

A Dito usa o FCM para envio das notificaões, o primeiro passo é você configurar as dependências do
FCM:

```
dependencies {
  implementation "com.google.firebase:firebase-bom:33.2.0"
  implementation "com.google.firebase:firebase-messaging:24.0.1"
}
```

E configurar o seu projeto seguindo da documentação oficial do
FCM. [Clique aqui](https://firebase.google.com/docs/cloud-messaging/android/client).

### Recuperar e registrar token

```
FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
    if (!task.isSuccessful) {
        Log.w("Dito", "Fetching FCM registration token failed", task.exception)
        return@OnCompleteListener
    }
    val token = task.result
    Dito.registerDevice(token!!)
})
```

### Deletar token

```
Dito.unregisterDevice(token)
```

### Registrar leitura da notificação

O metódo `notificationRead` recebe como parâmetro o ID da notificação que é enviado pela Dito, no
atributo data. Para interceptar as notificações consulte a documentação oficial do
FCM. [Clique aqui](https://firebase.google.com/docs/cloud-messaging/android/client).

```
Dito.notificationRead(notification)
```

### Notificação simples

A Dito disponibiliza uma forma simples para receber e identificar a leitura das notificações, só
adicionar as configurações abaixo no seu `AndroidManifest`:

```
<application>
  ...
    <service
        android:name="br.com.dito.ditosdk.notification.DitoMessagingService"
        android:exported="true"
        android:permission="POST_NOTIFICATIONS">
        <intent-filter>
            <action android:name="com.google.firebase.MESSAGING_EVENT" />
        </intent-filter>
    </service>

    <receiver android:name="br.com.dito.ditosdk.notification.NotificationOpenedReceiver" />
</application>
```
