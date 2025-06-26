package com.youtube.stage.init;

import ch.qos.logback.classic.encoder.JsonEncoder;
import com.youtube.stage.model.*;
import com.youtube.stage.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final VideoRepository videoRepository;
    private final PlaylistRepository playlistRepository;
    private final CommentRepository commentRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, VideoRepository videoRepository, PlaylistRepository playlistRepository, CommentRepository commentRepository, SubscriptionRepository subscriptionRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.videoRepository = videoRepository;
        this.playlistRepository = playlistRepository;
        this.commentRepository = commentRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            User user1 = new User();
            user1.setUsername("luisa");
            user1.setEmail("luisa@example.com");
            user1.setPasswordHash(passwordEncoder.encode("password"));
            user1.setRoles(Set.of("USER"));


            User user2 = new User();
            user2.setUsername("marco");
            user2.setEmail("marco@example.com");
            user2.setPasswordHash(passwordEncoder.encode("password"));
            user2.setRoles(Set.of("ADMIN"));

            user1.setFollowers(2000);
            user2.setFollowers(14000);

            userRepository.saveAll(List.of(user1, user2));

            Subscription subscription1 = new Subscription();
            subscription1.setSubscriber(user1);
            subscription1.setChannelOwner(user2);

            Subscription subscription2 = new Subscription();
            subscription2.setSubscriber(user2);
            subscription2.setChannelOwner(user1);

            subscriptionRepository.saveAll(List.of(subscription1, subscription2));


            Video video1 = new Video();
            video1.setTitle("Viaggio in Islanda");
            video1.setDescription("Un documentario sul paesaggio islandese");
            video1.setCategory("Viaggi");
            video1.setVideoUrl("https://example.com/iceland");
            video1.setThumbnailUrl("https://placehold.co/600x400");
            video1.setUploader(user1);
            video1.setViews(102);
            video1.setLikes(34);


            Video video2 = new Video();
            video2.setTitle("Ricetta Carbonara");
            video2.setDescription("Come cucinare una carbonara perfetta");
            video2.setCategory("Cucina");
            video2.setVideoUrl("https://example.com/carbonara");
            video2.setThumbnailUrl("https://placehold.co/600x400");
            video2.setUploader(user2);
            video2.setViews(102);
            video2.setLikes(34);

            Video video3 = new Video();
            video3.setTitle("Allenamento HIIT");
            video3.setDescription("Sessione di allenamento ad alta intensità");
            video3.setCategory("Fitness");
            video3.setVideoUrl("https://example.com/hiit");
            video3.setThumbnailUrl("https://placehold.co/600x400");
            video3.setUploader(user1);
            video3.setViews(102);
            video3.setLikes(34);

            Video video4 = new Video();
            video4.setTitle("Corso base di Java");
            video4.setDescription("Introduzione alla programmazione Java");
            video4.setCategory("Educazione");
            video4.setVideoUrl("https://example.com/java-course");
            video4.setThumbnailUrl("https://placehold.co/600x400");
            video4.setUploader(user1);
            video4.setViews(102);
            video4.setLikes(34);

            Video video5 = new Video();
            video5.setTitle("Come fare un bonsai");
            video5.setDescription("Guida pratica alla cura dei bonsai");
            video5.setCategory("Hobby");
            video5.setVideoUrl("https://example.com/bonsai");
            video5.setThumbnailUrl("https://placehold.co/600x400");
            video5.setUploader(user1);
            video5.setViews(102);
            video5.setLikes(34);

            Video video6 = new Video();
            video6.setTitle("Tour di Tokyo");
            video6.setDescription("Esploriamo Tokyo in 24 ore");
            video6.setCategory("Viaggi");
            video6.setVideoUrl("https://example.com/tokyo-tour");
            video6.setThumbnailUrl("https://placehold.co/600x400");
            video6.setUploader(user1);
            video6.setViews(102);
            video6.setLikes(34);



            Video video7 = new Video();
            video7.setTitle("Gnocchi fatti in casa");
            video7.setDescription("La ricetta tradizionale degli gnocchi italiani");
            video7.setCategory("Cucina");
            video7.setVideoUrl("https://example.com/gnocchi");
            video7.setThumbnailUrl("https://placehold.co/600x400");
            video7.setUploader(user2);
            video7.setViews(102);
            video7.setLikes(34);

            Video video8 = new Video();
            video8.setTitle("Tutorial Photoshop base");
            video8.setDescription("Modifica immagini con Photoshop: le basi");
            video8.setCategory("Design");
            video8.setVideoUrl("https://example.com/photoshop");
            video8.setThumbnailUrl("https://placehold.co/600x400");
            video8.setUploader(user2);
            video8.setViews(102);
            video8.setLikes(34);

            Video video9 = new Video();
            video9.setTitle("Come imparare l'inglese velocemente");
            video9.setDescription("Consigli per studiare inglese in modo efficace");
            video9.setCategory("Educazione");
            video9.setVideoUrl("https://example.com/english");
            video9.setThumbnailUrl("https://placehold.co/600x400");
            video9.setUploader(user2);
            video9.setViews(102);
            video9.setLikes(34);

            Video video10 = new Video();
            video10.setTitle("Workout a corpo libero");
            video10.setDescription("Allenati ovunque senza attrezzi");
            video10.setCategory("Fitness");
            video10.setVideoUrl("https://example.com/bodyweight");
            video10.setThumbnailUrl("https://placehold.co/600x400");
            video10.setUploader(user2);
            video10.setViews(102);
            video10.setLikes(34);

            // 🔹 Per user1
            Video video11 = new Video();
            video11.setTitle("Come organizzare il tuo tempo");
            video11.setDescription("Tecniche di time management per essere più produttivi");
            video11.setCategory("Educazione");
            video11.setVideoUrl("https://example.com/time-management");
            video11.setThumbnailUrl("https://placehold.co/600x400");
            video11.setUploader(user1);
            video11.setViews(77);
            video11.setLikes(21);

            Video video12 = new Video();
            video12.setTitle("Stretching mattutino");
            video12.setDescription("Routine di stretching per iniziare bene la giornata");
            video12.setCategory("Wellness");
            video12.setVideoUrl("https://example.com/stretching");
            video12.setThumbnailUrl("https://placehold.co/600x400");
            video12.setUploader(user1);
            video12.setViews(98);
            video12.setLikes(28);

            Video video13 = new Video();
            video13.setTitle("Montaggio video per principianti");
            video13.setDescription("Guida base all'editing con DaVinci Resolve");
            video13.setCategory("Design");
            video13.setVideoUrl("https://example.com/video-editing");
            video13.setThumbnailUrl("https://placehold.co/600x400");
            video13.setUploader(user1);
            video13.setViews(142);
            video13.setLikes(39);

            Video video14 = new Video();
            video14.setTitle("Impara le basi del francese");
            video14.setDescription("Lezioni rapide per iniziare a parlare francese");
            video14.setCategory("Educazione");
            video14.setVideoUrl("https://example.com/french-basics");
            video14.setThumbnailUrl("https://placehold.co/600x400");
            video14.setUploader(user1);
            video14.setViews(211);
            video14.setLikes(50);

            Video video15 = new Video();
            video15.setTitle("5 ricette vegane facili e veloci");
            video15.setDescription("Idee per piatti gustosi senza ingredienti animali");
            video15.setCategory("Cucina");
            video15.setVideoUrl("https://example.com/ricette-vegane");
            video15.setThumbnailUrl("https://placehold.co/600x400");
            video15.setUploader(user1);
            video15.setViews(165);
            video15.setLikes(44);


// 🔹 Per user2
            Video video16 = new Video();
            video16.setTitle("Allenamento HIIT avanzato");
            video16.setDescription("Sessione intensa per bruciare grassi in 15 minuti");
            video16.setCategory("Fitness");
            video16.setVideoUrl("https://example.com/hiit");
            video16.setThumbnailUrl("https://placehold.co/600x400");
            video16.setUploader(user2);
            video16.setViews(220);
            video16.setLikes(61);

            Video video17 = new Video();
            video17.setTitle("Come creare un logo con Illustrator");
            video17.setDescription("Tutorial semplice per principianti");
            video17.setCategory("Design");
            video17.setVideoUrl("https://example.com/logo-illustrator");
            video17.setThumbnailUrl("https://placehold.co/600x400");
            video17.setUploader(user2);
            video17.setViews(144);
            video17.setLikes(32);

            Video video18 = new Video();
            video18.setTitle("Pranzo sano in 10 minuti");
            video18.setDescription("3 idee di pasti sani e veloci da preparare");
            video18.setCategory("Cucina");
            video18.setVideoUrl("https://example.com/pranzo-sano");
            video18.setThumbnailUrl("https://placehold.co/600x400");
            video18.setUploader(user2);
            video18.setViews(197);
            video18.setLikes(47);

            Video video19 = new Video();
            video19.setTitle("Meditazione guidata per dormire");
            video19.setDescription("Rilassati con questa meditazione serale");
            video19.setCategory("Wellness");
            video19.setVideoUrl("https://example.com/meditazione-sonno");
            video19.setThumbnailUrl("https://placehold.co/600x400");
            video19.setUploader(user2);
            video19.setViews(183);
            video19.setLikes(53);

            Video video20 = new Video();
            video20.setTitle("Parla in pubblico senza paura");
            video20.setDescription("Tecniche per migliorare la comunicazione orale");
            video20.setCategory("Educazione");
            video20.setVideoUrl("https://example.com/public-speaking");
            video20.setThumbnailUrl("https://placehold.co/600x400");
            video20.setUploader(user2);
            video20.setViews(174);
            video20.setLikes(38);



            video1.getLikedByUsers().add(user2);
            video2.getLikedByUsers().add(user1);
            video4.getLikedByUsers().add(user2);
            video6.getLikedByUsers().add(user2);

            videoRepository.saveAll(List.of(
                    video1, video2, video3, video4, video5,
                    video6, video7, video8, video9, video10,
                    video11, video12, video13, video14, video15,
                    video16, video17, video18, video19, video20
            ));

            Playlist playlist1 = new Playlist();
            playlist1.setName("Viaggi mozzafiato");
            playlist1.setDescription("Video delle mie esplorazioni");
            playlist1.setPublic(true);
            playlist1.setOwner(user1);
            playlist1.setVideos(List.of(video1, video6)); // Viaggio in Islanda, Tour di Tokyo

            Playlist playlist2 = new Playlist();
            playlist2.setName("Ricette italiane");
            playlist2.setDescription("Cucina semplice e gustosa");
            playlist2.setPublic(true);
            playlist2.setOwner(user2);
            playlist2.setVideos(List.of(video2, video7)); // Carbonara, Gnocchi

            playlistRepository.saveAll(List.of(playlist1, playlist2));

            Comment comment1 = new Comment();
            comment1.setContent("Bellissimo paesaggio, voglio andarci!");
            comment1.setAuthor(user2);
            comment1.setVideo(video1);

            Comment comment2 = new Comment();
            comment2.setContent("Ottima spiegazione, mi è venuta buonissima!");
            comment2.setAuthor(user1);
            comment2.setVideo(video2);

            Comment comment3 = new Comment();
            comment3.setContent("Grazie per il tutorial, molto utile!");
            comment3.setAuthor(user2);
            comment3.setVideo(video4); // Corso Java

            commentRepository.saveAll(List.of(comment1, comment2, comment3));


            System.out.println("Dati iniziali caricati");
        }
    }
}

