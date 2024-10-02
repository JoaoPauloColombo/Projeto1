package com.example.greenspot;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap gMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.id_map);
        mapFragment.getMapAsync(this);

    }
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        // Definindo a primeira coordenada do local (latitude e longitude)
        LatLng Astarte = new LatLng(-23.54926754847192, -46.52485532327169);
        // Adicionando um marcador no primeiro local
        googleMap.addMarker(new MarkerOptions().position(Astarte).title("Astarte EcoPonto"));


        LatLng NovaYork = new LatLng(-23.58756121929513, -46.509033962556316);

        googleMap.addMarker(new MarkerOptions().position(NovaYork).title("Vila Nova York EcoPonto"));

        LatLng ViadutoEngenheiro = new LatLng(-23.537087399449167, -46.547105533721975);

        googleMap.addMarker(new MarkerOptions().position(ViadutoEngenheiro).title("Viaduto Eng.º Alberto Badra EcoPonto"));

        LatLng JardimMariadoCarmo = new LatLng(-23.59534156918905, -46.74678709139224);

        googleMap.addMarker(new MarkerOptions().position(JardimMariadoCarmo).title("Jardim Maria do Carmo EcoPonto"));

        LatLng GiovaniGronchi	 = new LatLng(-23.609928040431623, -46.72680061837327);

        googleMap.addMarker(new MarkerOptions().position(GiovaniGronchi).title("Giovani Gronchi EcoPonto"));

        LatLng JardimJaqueline   = new LatLng(-23.588974732285358, -46.75499947419204);

        googleMap.addMarker(new MarkerOptions().position(JardimJaqueline).title("Jardim Jaqueline EcoPonto"));

        LatLng Politecnica   = new LatLng(-23.57580854570858, -46.76496465884694);

        googleMap.addMarker(new MarkerOptions().position(Politecnica).title("Politécnica EcoPonto"));

        LatLng Olinda   = new LatLng(-23.631553204790144, -46.75765713823425);

        googleMap.addMarker(new MarkerOptions().position(Olinda).title("Olinda EcoPonto"));

        LatLng SantoDias   = new LatLng(-23.663698210851038, -46.77672916440785);

        googleMap.addMarker(new MarkerOptions().position(SantoDias).title("Santo Dias EcoPonto"));

        LatLng ParqueFernanda   = new LatLng(-23.671767743687745, -46.7922134606976);

        googleMap.addMarker(new MarkerOptions().position(ParqueFernanda).title("Parque Fernanda EcoPonto"));

        LatLng ViladasBelezas   = new LatLng(-23.637800976843906, -46.74173113557259);

        googleMap.addMarker(new MarkerOptions().position(ViladasBelezas).title("Vila das Belezas EcoPonto"));

        LatLng Paraisópolis   = new LatLng(-23.622913765963123, -46.725136328798655);

        googleMap.addMarker(new MarkerOptions().position(Paraisópolis).title("Paraisópolis EcoPonto"));

        LatLng CidadeSaudavel	   = new LatLng(-23.671767743687745, -46.7922134606976);

        googleMap.addMarker(new MarkerOptions().position(CidadeSaudavel	).title("Cidade Saudável EcoPonto"));

        LatLng ParquePeruche	   = new LatLng(-23.49144224685657, -46.65563783001409);

        googleMap.addMarker(new MarkerOptions().position(ParquePeruche	).title("Parque Peruche EcoPonto"));

        LatLng VilaNovaCachoeirinha	   = new LatLng(-23.47205055754343, -46.669809974197044);

        googleMap.addMarker(new MarkerOptions().position(VilaNovaCachoeirinha	).title("Vila Nova Cachoeirinha EcoPonto"));

        LatLng VilaSantaMaria	   = new LatLng(-23.493903445017107, -46.671607662560206);

        googleMap.addMarker(new MarkerOptions().position(VilaSantaMaria	).title("Vila Santa Maria EcoPonto"));

        LatLng JardimAntartica	   = new LatLng(-23.45486743193258, -46.65949628954335);

        googleMap.addMarker(new MarkerOptions().position(JardimAntartica	).title("Jardim Antartica EcoPonto"));

        LatLng Alvarenga	   = new LatLng(-23.693732163189463, -46.65209777560899);

        googleMap.addMarker(new MarkerOptions().position(Alvarenga	).title("Alvarenga EcoPonto"));

        LatLng Cupece		   = new LatLng(-23.665109846508855, -46.654292431861634);

        googleMap.addMarker(new MarkerOptions().position(Cupece		).title("Cupecê EcoPonto"));

        LatLng NascerdoSol		   = new LatLng(-23.593638327008684, -46.41525009139237);

        googleMap.addMarker(new MarkerOptions().position(NascerdoSol		).title("Nascer do Sol EcoPonto"));

        LatLng  SetorG	   = new LatLng(-23.493903445017107, -46.671607662560206);

        googleMap.addMarker(new MarkerOptions().position( SetorG	).title(" Setor G EcoPonto"));

        LatLng ParqueBoturussu	   = new LatLng(-23.503668374025104, -46.48498491533522);

        googleMap.addMarker(new MarkerOptions().position(ParqueBoturussu	).title("Parque Boturussu EcoPonto"));

        LatLng JardimSaoNicolau		   = new LatLng(-23.693732163189463, -46.65209777560899);

        googleMap.addMarker(new MarkerOptions().position(JardimSaoNicolau	).title("Jardim São Nicolau EcoPonto"));

        LatLng VilaRica			   = new LatLng(-23.469758913884426, -46.674893562561266);

        googleMap.addMarker(new MarkerOptions().position(VilaRica			).title("Vila Rica EcoPonto"));

        LatLng FreguesiadoO 		   = new LatLng(-23.505647900432308, -46.704463347214045);

        googleMap.addMarker(new MarkerOptions().position(FreguesiadoO		).title("Freguesia do Ó EcoPonto"));

        LatLng  Bandeirantes	   = new LatLng(-23.486146178607232, -46.695216833724245);

        googleMap.addMarker(new MarkerOptions().position( Bandeirantes	).title(" Bandeirantes EcoPonto"));

        LatLng Guaiaponto		   = new LatLng(-23.555048946642252, -46.41292094535702);

        googleMap.addMarker(new MarkerOptions().position(Guaiaponto		).title("Guaiaponto EcoPonto"));

        LatLng Lajeado		   = new LatLng(-23.530750818518875, -46.40618496255869);

        googleMap.addMarker(new MarkerOptions().position(Lajeado	).title("Lajeado EcoPonto"));

        LatLng JardimSaoPaulo			   = new LatLng(-23.565272033170505, -46.398384076047925);

        googleMap.addMarker(new MarkerOptions().position(JardimSaoPaulo			).title("Jardim São Paulo EcoPonto"));

        LatLng TerezaCristina			   = new LatLng(-23.56890966842056, -46.608514564411855);

        googleMap.addMarker(new MarkerOptions().position( TerezaCristina			).title(" Tereza Cristina EcoPonto"));

        LatLng ViladasMerces 		   = new LatLng(-23.62359725446521, -46.605684818372616);

        googleMap.addMarker(new MarkerOptions().position(ViladasMerces		).title("Vila das Mercês EcoPonto"));

        LatLng  Heliopolis		   = new LatLng(-23.600024380699484, -46.59724306270527);

        googleMap.addMarker(new MarkerOptions().position( Heliopolis		).title(" Heliópolis EcoPonto"));

        LatLng SantaCruz			   = new LatLng(-23.600011257215975, -46.59717861695141);

        googleMap.addMarker(new MarkerOptions().position(SantaCruz			).title("Santa Cruz EcoPonto"));

        LatLng MaePreta		   = new LatLng(-23.508352465108565, -46.41673767419548);

        googleMap.addMarker(new MarkerOptions().position(MaePreta	).title("Mãe Preta EcoPonto"));

        LatLng Pesqueiro				   = new LatLng(-23.508254081439382, -46.41684496255964);

        googleMap.addMarker(new MarkerOptions().position(Pesqueiro				).title("Pesqueiro EcoPonto"));

        LatLng Flamingo	 		   = new LatLng(-23.51417739293976, -46.42119397605002);

        googleMap.addMarker(new MarkerOptions().position(Flamingo			).title("Flamingo EcoPonto"));

        LatLng  Moreira	   = new LatLng(-23.49737786951991, -46.37081801837796);

        googleMap.addMarker(new MarkerOptions().position( Moreira	).title(" Moreira EcoPonto"));

        LatLng CidadeLider		   = new LatLng(-23.56260506138979, -46.49702144535674);

        googleMap.addMarker(new MarkerOptions().position(CidadeLider		).title("Cidade Lider EcoPonto"));

        LatLng OsvaldoValledeCordeiro		   = new LatLng(-23.556527626956605, -46.49170247790326);

        googleMap.addMarker(new MarkerOptions().position(OsvaldoValledeCordeiro	).title("Osvaldo Valle de Cordeiro EcoPonto"));

        LatLng ParquedoCarmo = new LatLng(-23.565272033170505, -46.398384076047925);

        googleMap.addMarker(new MarkerOptions().position(ParquedoCarmo).title("Parque do Carmo EcoPonto"));

        LatLng ParqueGuarani			   = new LatLng(-23.52064174292003, -46.46363326255899);

        googleMap.addMarker(new MarkerOptions().position( ParqueGuarani			).title(" Parque Guarani EcoPonto"));

        LatLng Cohab 		   = new LatLng(-23.55443987334037, -46.94771982695933);

        googleMap.addMarker(new MarkerOptions().position(Cohab		).title("Cohab EcoPonto"));

        LatLng  Jabaquara		   = new LatLng(-23.652918335578345, -46.64964224535284);

        googleMap.addMarker(new MarkerOptions().position( Jabaquara		).title(" Jabaquara EcoPonto"));

        LatLng Imigrantes			   = new LatLng(-23.632160713351546, -46.630565533717814);

        googleMap.addMarker(new MarkerOptions().position(Imigrantes			).title("Imigrantes EcoPonto"));

        LatLng ViadutoAntartica		   = new LatLng(-23.524304932812395, -46.67066216255886);

        googleMap.addMarker(new MarkerOptions().position(ViadutoAntartica	).title("Viaduto Antártica EcoPonto"));

        LatLng VilaJaguara			   = new LatLng(-23.516195272376233, -46.74198350488638);

        googleMap.addMarker(new MarkerOptions().position(VilaJaguara				).title("Vila Jaguará EcoPonto"));

        LatLng Piraporinha			   = new LatLng(-23.669677632155395, -46.736469676043505);

        googleMap.addMarker(new MarkerOptions().position( Piraporinha			).title(" Piraporinha EcoPonto"));

        LatLng Bresser 		   = new LatLng(-23.543640343000256, -46.60605255884834);

        googleMap.addMarker(new MarkerOptions().position(Bresser		).title("Bresser EcoPonto"));

        LatLng  Tatuape		   = new LatLng(-23.52978166421638, -46.58307724906797);

        googleMap.addMarker(new MarkerOptions().position( Tatuape		).title(" Tatuapé EcoPonto"));

        LatLng Bras			   = new LatLng(-23.554447680599786, -46.61085707604838);

        googleMap.addMarker(new MarkerOptions().position(Bras			).title("Brás EcoPonto"));

        LatLng AguaRasa			   = new LatLng(-23.543575028623213, -46.580611447212384);

        googleMap.addMarker(new MarkerOptions().position(AguaRasa	).title("Água Rasa EcoPonto"));

        LatLng MendesCaldeira					   = new LatLng(-23.539085129730353, -46.62224449139462);

        googleMap.addMarker(new MarkerOptions().position(MendesCaldeira					).title("Mendes Caldeira EcoPonto"));

        LatLng Pari		 		   = new LatLng(-23.524554918764775, -46.60939411837679);

        googleMap.addMarker(new MarkerOptions().position(Pari				).title("Pari EcoPonto"));

        LatLng  Mooca	   = new LatLng(-23.54777650602346, -46.60283343372145);

        googleMap.addMarker(new MarkerOptions().position( Mooca	).title(" Mooca EcoPonto"));

        LatLng Belem		   = new LatLng(-23.543002606523153, -46.593659487684675);

        googleMap.addMarker(new MarkerOptions().position(Belem		).title("Belém EcoPonto"));

        LatLng VilaLuisa		   = new LatLng(-23.530927778853393, -46.55241691837662);

        googleMap.addMarker(new MarkerOptions().position(VilaLuisa	).title("Vila Luisa EcoPonto"));

        LatLng PenhaI = new LatLng(-23.531292095574656, -46.5253268741944);

        googleMap.addMarker(new MarkerOptions().position(PenhaI).title("Penha I EcoPonto"));

        LatLng Tiquatira				   = new LatLng(-23.513281446964367, -46.543900108596446);

        googleMap.addMarker(new MarkerOptions().position( Tiquatira				).title(" Tiquatira EcoPonto"));

        LatLng Cipoaba 		   = new LatLng(-23.52313875945019, -46.65543919998521);

        googleMap.addMarker(new MarkerOptions().position(Cipoaba		).title("Cipoaba EcoPonto"));

        LatLng  Franquinho		   = new LatLng(-23.532967085290686, -46.49030991634413);

        googleMap.addMarker(new MarkerOptions().position( Franquinho		).title(" Franquinho EcoPonto"));

        LatLng Gamelinha			   = new LatLng(-23.550573834042133, -46.49493330488486);

        googleMap.addMarker(new MarkerOptions().position(Gamelinha			).title("Gamelinha EcoPonto"));

        LatLng VilaMatilde			   = new LatLng(-23.539083785521616, -46.50505756255829);

        googleMap.addMarker(new MarkerOptions().position(VilaMatilde		).title("Vila Matilde EcoPonto"));

        LatLng VilaTalarico			   = new LatLng(-23.538966368987545, -46.51570176070329);

        googleMap.addMarker(new MarkerOptions().position(VilaTalarico				).title("Vila Talarico EcoPonto"));

        LatLng CohabArturAlvim	   = new LatLng(-23.548897272297715, -46.48328249139418);

        googleMap.addMarker(new MarkerOptions().position(CohabArturAlvim	).title("Cohab Artur Alvim EcoPonto"));

        LatLng Dalila	   = new LatLng(-23.550521921118595, -46.52230710302995);

        googleMap.addMarker(new MarkerOptions().position(Dalila	).title("Dalila EcoPonto"));

        LatLng RecantodosHumildes	   = new LatLng(-23.40890132622593, -46.75059937419956);

        googleMap.addMarker(new MarkerOptions().position(RecantodosHumildes	).title("Recanto dos Humildes EcoPonto"));

        LatLng JardimSantaFe		   = new LatLng(-23.431365491585563, -46.791907833726434);

        googleMap.addMarker(new MarkerOptions().position(JardimSantaFe		).title("Jardim Santa Fé EcoPonto"));

        LatLng VilaMadalena = new LatLng(-23.558023089856476, -46.68738942023021);

        googleMap.addMarker(new MarkerOptions().position(VilaMadalena	).title("Vila Madalena EcoPonto"));

        LatLng AltodePinheiros		   = new LatLng(-23.55682127581531, -46.71056269139398);

        googleMap.addMarker(new MarkerOptions().position(AltodePinheiros		).title("Alto de Pinheiros EcoPonto"));

        LatLng ConegoJoseSalomon		   = new LatLng(-23.49218024142614, -46.72195133372387);

        googleMap.addMarker(new MarkerOptions().position(ConegoJoseSalomon		).title("Cônego José Salomon EcoPonto"));

        LatLng VigarioGodoi		   = new LatLng(-23.477443606241486, -46.71803871837879);

        googleMap.addMarker(new MarkerOptions().position(VigarioGodoi		).title("Vigário Godoi EcoPonto"));

        LatLng  Voith	   = new LatLng(-23.44508703271234, -46.73952723372587);

        googleMap.addMarker(new MarkerOptions().position( Voith	).title(" Voith EcoPonto"));

        LatLng AlexiosJafet	   = new LatLng(-23.452725301247796, -46.749729220234855);

        googleMap.addMarker(new MarkerOptions().position(AlexiosJafet	).title("Alexios Jafet EcoPonto"));

        LatLng ZakiNarchi		   = new LatLng(-23.51101642149097, -46.621580759515616);

        googleMap.addMarker(new MarkerOptions().position(ZakiNarchi	).title("Zaki Narchi EcoPonto"));

        LatLng Tucuruvi			   = new LatLng(-23.46714154021199, -46.60999844907063);

        googleMap.addMarker(new MarkerOptions().position(Tucuruvi			).title("Tucuruvi EcoPonto"));

        LatLng AlceuMaynarddeAraujo 		   = new LatLng(-23.635925675469192, -46.71368713186276);

        googleMap.addMarker(new MarkerOptions().position(AlceuMaynarddeAraujo		).title("Alceu Maynard de Araújo"));

        LatLng  VicenteRao	   = new LatLng(-23.6346774186349, -46.67897108953569);

        googleMap.addMarker(new MarkerOptions().position( VicenteRao	).title(" Vicente Rao EcoPonto"));


        LatLng LimaBonfante			   = new LatLng(-23.628416214409466, -46.44658070488175);

        googleMap.addMarker(new MarkerOptions().position(LimaBonfante		).title("Lima Bonfante EcoPonto"));

        LatLng Montalvania			   = new LatLng(-23.57908724889877, -46.495489562556465);

        googleMap.addMarker(new MarkerOptions().position(Montalvania			).title("Montalvania EcoPonto"));

        LatLng Iguatemi			   = new LatLng(-23.606143820074674, -46.45170146070052);

        googleMap.addMarker(new MarkerOptions().position( Iguatemi			).title(" Iguatemi EcoPonto"));

        LatLng Imperador 		   = new LatLng(-23.513808295739242, -46.456573603031636);

        googleMap.addMarker(new MarkerOptions().position(Imperador		).title("Imperador EcoPonto"));

        LatLng  CarlitoMaia		   = new LatLng(-23.489899915500857, -46.39363564417476);

        googleMap.addMarker(new MarkerOptions().position( CarlitoMaia		).title(" Carlito Maia EcoPonto"));

        LatLng Itaqueruna			   = new LatLng(-23.50975906678875, -46.43278074721394);

        googleMap.addMarker(new MarkerOptions().position(Itaqueruna			).title("Itaqueruna EcoPonto"));

        LatLng  PedroNunes		   = new LatLng(-23.50637643974809, -46.46233493557821);

        googleMap.addMarker(new MarkerOptions().position( PedroNunes	).title(" Pedro Nunes EcoPonto"));

        LatLng SitioOratorio					   = new LatLng(-23.623235040670036, -46.509216818372565);

        googleMap.addMarker(new MarkerOptions().position(SitioOratorio					).title("Sitio Oratório EcoPonto"));

        LatLng TeotonioVilela		 		   = new LatLng(-23.623067934598545, -46.50927046255463);

        googleMap.addMarker(new MarkerOptions().position(TeotonioVilela				).title("Teotônio Vilela EcoPonto"));

        LatLng  Glicerio	   = new LatLng(-23.55392417981661, -46.62634131837564);

        googleMap.addMarker(new MarkerOptions().position( Glicerio	).title(" Glicério EcoPonto"));

        LatLng Liberdade		   = new LatLng(-23.556416453056617, -46.63715637419326);

        googleMap.addMarker(new MarkerOptions().position(Liberdade		).title("Liberdade EcoPonto"));

        LatLng Armenia		   = new LatLng(-23.52131063012905, -46.62762780488611);

        googleMap.addMarker(new MarkerOptions().position(Armenia	).title("Armênia EcoPonto"));

        LatLng BarraFunda = new LatLng(-23.526922855663024, -46.648500213474875);

        googleMap.addMarker(new MarkerOptions().position(BarraFunda).title("Barra Funda EcoPonto"));

        LatLng Cambuci			   = new LatLng(-23.56453452496397, -46.61131682023007);

        googleMap.addMarker(new MarkerOptions().position( Cambuci			).title(" Cambuci EcoPonto"));

        LatLng VilaGuilherme = new LatLng(-23.517022039410172, -46.60042696255926);

        googleMap.addMarker(new MarkerOptions().position(VilaGuilherme).title("Vila Guilherme EcoPonto"));

        LatLng  VilaMaria		   = new LatLng(-23.519499378425316, -46.580948606741245);

        googleMap.addMarker(new MarkerOptions().position( VilaMaria		).title(" Vila Maria EcoPonto"));

        LatLng VilaSabrina			   = new LatLng(-23.48624289872742, -46.56561817605127);

        googleMap.addMarker(new MarkerOptions().position(VilaSabrina			).title("Vila Sabrina EcoPonto"));

        LatLng Mirandopolis		   = new LatLng(-23.611561635512405, -46.64445998953665);

        googleMap.addMarker(new MarkerOptions().position(Mirandopolis	).title("Mirandópolis EcoPonto"));

        LatLng Saioa			   = new LatLng(-23.59428677216276, -46.62238487419183);

        googleMap.addMarker(new MarkerOptions().position(Saioa				).title("Saioá EcoPonto"));

        LatLng VilaMariana				   = new LatLng(-23.592829790413482, -46.63484803186467);

        googleMap.addMarker(new MarkerOptions().position( VilaMariana				).title(" Vila Mariana EcoPonto"));

        LatLng AnhaiaMello 		   = new LatLng(-23.582457153750283, -46.57103048953798);

        googleMap.addMarker(new MarkerOptions().position(AnhaiaMello		).title("Anhaia Mello EcoPonto"));

        LatLng  SaoLucas		   = new LatLng(-23.59889764715276, -46.53442218953711);

        googleMap.addMarker(new MarkerOptions().position( SaoLucas		).title("São Lucas EcoPonto"));


        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Astarte, 13));
    }


}