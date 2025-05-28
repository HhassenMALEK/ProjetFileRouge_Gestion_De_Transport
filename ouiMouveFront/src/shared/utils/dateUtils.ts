/**
 * Extrait la date et l'heure d'une chaîne de caractères de date.
 * @param dateString La chaîne de caractères de date à analyser (par exemple, "2024-05-27T10:30:00").
 * @returns Un objet avec les propriétés `date` (format JJ/MM/AAAA) et `time` (format HH:mm), 
 *          ou null si la chaîne de date est invalide.
 */
export function extractDateTime(dateString?: string): { date: string; time: string } | null {
  if (!dateString) {
    return null;
  }

  try {
    const dateObj = new Date(dateString);

    // Vérifier si la date est valide
    if (isNaN(dateObj.getTime())) {
      console.error('Invalid date string provided:', dateString);
      return null;
    }

    const day = dateObj.getDate().toString().padStart(2, '0');
    const month = (dateObj.getMonth() + 1).toString().padStart(2, '0'); // Les mois sont indexés à partir de 0
    const year = dateObj.getFullYear();

    const hours = dateObj.getHours().toString().padStart(2, '0');
    const minutes = dateObj.getMinutes().toString().padStart(2, '0');

    return {
      date: `${day}/${month}/${year}`,
      time: `${hours}:${minutes}`,
    };
  } catch (error) {
    console.error('Error parsing date string:', dateString, error);
    return null;
  }
}