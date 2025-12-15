package be.feastord.feastord.frontend.util;

import be.feastord.feastord.frontend.dto.ReservationDto;
import be.feastord.feastord.frontend.model.TypeRole;

public class SessionManager {

    // 🔴 TEMPORAIRE (sera remplacé par le login réel)
    private static Integer currentUserId = 1;
    private static TypeRole currentUserRole = TypeRole.CLIENT;

    private static Integer selectedRestaurantId;

    private static ReservationDto reservationToEdit;

    public static void setReservationToEdit(ReservationDto r) {
        reservationToEdit = r;
    }

    public static ReservationDto getReservationToEdit() {
        return reservationToEdit;
    }


    // ----- Utilisateur connecté -----

    public static Integer getCurrentUserId() {
        return currentUserId;
    }

    public static TypeRole getCurrentUserRole() {
        return currentUserRole;
    }

    // ----- Restaurant sélectionné -----

    public static void setSelectedRestaurantId(Integer id) {
        selectedRestaurantId = id;
    }

    public static Integer getSelectedRestaurantId() {
        return selectedRestaurantId;
    }

    // ----- À UTILISER PLUS TARD -----
    // Ces méthodes seront appelées par le LoginController
    public static void login(Integer userId, TypeRole role) {
        currentUserId = userId;
        currentUserRole = role;
    }

    public static void logout() {
        currentUserId = null;
        currentUserRole = null;
    }
}
