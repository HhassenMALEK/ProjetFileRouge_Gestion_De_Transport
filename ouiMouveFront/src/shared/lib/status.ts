type ColorLabelPair = {
    color: string;
    label: string;
};

export const apiStatus: Record<string, ColorLabelPair> = {
    BOOKING_OPEN: {
        color: '#4CAF50', 
        label: 'Réservation ouverte'
    },
    IN_PROGRESS: {
        color: '#4CAF50', 
        label: 'Réservation en cours'
    },
    FINSHED: {
        color: '#b53d3d', 
        label: 'Réservation terminée'
    },
    CANCELLED: {
        color: '#b53d3d', 
        label: 'Réservation annulée'
    },
    BOOKING_FULL: {
        color: '#b53d3d', 
        label: 'Il n’y a plus de place'
    }
}

