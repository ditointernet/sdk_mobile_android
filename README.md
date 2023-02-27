# Dito Android SDK

[![](https://jitpack.io/v/ditointernet/sdk_mobile_android.svg)](https://jitpack.io/#ditointernet/sdk_mobile_android)

## Dependência

No build.gradle da diretório raiz do projeto você deve adicionar a URL do repositiro

```
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```
E depois adiciona a dependência:

```
dependencies {
  implementation 'com.github.ditointernet:sdk_mobile_android:<LAST_VERSION>'
}
```
Para aplicativos com target Android 12 ou maior (API 31 +) usar o rc08

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
  <uses-permission android:name="android.permission.INTERNET" />
```

## Inicialização

No metodo onCreate da sua classe que estende de `Application` você deve iniciar o SDK:

```
override fun onCreate() {
  super.onCreate()
  Dito.init(this, Options(2)
}
```

`Options` não é uma configuração obrigatório, e você pode usa-lo para as seguintes configurações:

* **retry** (default = 5) - É numero de tentativas que o SDK vai fazer de envio do evento.

* **iconNotification** - É o Ressource ID do icone para notificação

* **contentIntent** - É a Intent que será aberta quando o usuário clicar na notificação. Caso esse valor essa *null*, vamos tentar recuperar a Activity que está configurada como `<action android:name="android.intent.action.MAIN" />`.

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

A Dito usa o FCM para envio das notificaões, o primeiro passo é você configurar as dependências do FCM:

```
dependencies {
  implementation 'com.google.firebase:firebase-core:16.0.4'
  implementation "com.google.firebase:firebase-messaging:17.3.4"
}
```

E configurar o seu projeto seguindo da documentação oficial do FCM. [Clique aqui](https://firebase.google.com/docs/cloud-messaging/android/client).

### Recuperar e registrar token

```
FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener {
  it?.token?.let {
    Dito.registerDevice(it)
  }
}
```

### Deletar token

```
Dito.unregisterDevice(token)
```

### Registrar leitura da notificação

O metódo `notificationRead` recebe como parâmetro o ID da notificação que é enviado pela Dito, no atributo data. Para interceptar as notificações consulte a documentação oficial do FCM. [Clique aqui](https://firebase.google.com/docs/cloud-messaging/android/client).

```
Dito.notificationRead(notification)
```

### Notificação simples

A Dito disponibiliza uma forma simples para receber e identificar a leitura das notificações, só adicionar as configurações abaixo no seu `AndroidManifest`:

```
<application>
  ...
  <service android:name="br.com.dito.ditosdk.notification.DitoMessagingService">
    <intent-filter>
      <action android:name="com.google.firebase.MESSAGING_EVENT" />
    </intent-filter>
  </service>

  <receiver android:name="br.com.dito.ditosdk.notification.NotificationOpenedReceiver"/>
</application>
```

