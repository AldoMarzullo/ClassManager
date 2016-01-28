package it.unical.classmanager.model.dao;

import java.util.List;

import it.unical.classmanager.model.data.Professor;
import it.unical.classmanager.model.data.Student;

/**
 * @author Aloisius92
 * This is a DAO for perform queries related to statistics.
 */
public interface CartQueryDAO {
    /********************************************/
    /**********     Professor Query    **********/
    /********************************************/
    
    /** Grafico professori e numero corsi che sostengono
     * 
     * @param professor
     * @return
     */
    public List<Object[]> getCourseByProfessor(Professor professor);
    
    
    /**Per professore: 
     * Per ogni anno, 
     * il numero totale di lezioni
     * per ogni giorno della settimana
     * di un professore.
     * 
     * @param professor
     * @return
     */
    public List<Object[]> getForYearLectureByWeekDay(Professor professor);
       
    /**Per professore: 
     * Negli anni, 
     * il numero medio di lezioni
     * per ogni giorno della settimana
     * di un professore.
     * 
     * @param professor
     * @return
     */
    public List<Object[]> getAvgLectureByWeekDaySingleProfessor(Professor professor);
     
    /**Tra professori: 
     * Negli anni, 
     * il numero medio di lezioni
     * per ogni giorno della settimana.
     * 
     * @param professor
     * @return
     */
    public List<Object[]> getAvgLectureByWeekDayAllProfessor(List<Professor> professors);
    
    /**Grafico Studenti che volgono i compiti dati: Tempo di consegna
     * Chi consegna prima? 
     * Per ogni corso, tra gli studenti iscritti,
     * calcola la media delle differenze tra
     * il giorno di asseganzione del compito
     * e il giorno di consegna.
     * 
     * @param professor
     * @return
     */
    public List<Object[]> getAvgTimeDeliveryHomework(Professor professor, String research);
    
    /**Grafico Studenti che volgono i compiti dati: Valutazione
     * Chi prende i voti migliori?
     * Per ogni corso, tra gli studenti iscritti,
     * calcola la media tra le valutazioni dei compiti,
     * (se uno studente non svolge il compito allora valutazione 0).
     * 
     * @param professor
     * @param research 
     * @return
     */
    public List<Object[]> getAvgScoreHomework(Professor professor, String research);
    
    /**Grafico presenze Studenti
     * Per ogni corso
     * la media delle presenze
     * di tutti gli studenti.
     * 
     * @param professor
     * @return
     */
    public List<Object[]> getAvgAttendanceStudent(Professor professor);
       
    /********************************************/
    /**********      Student Query     **********/
    /********************************************/
    
    /**
     * La media dei compiti per ogni corso.
     * 
     * @param student
     * @return
     */
    public List<Object[]> getAvgHomeworksByStudent(Student student); 
    
    /**
     * La media dei tempi di consegna
     * dei compiti per ogni corso.
     * 
     * @param student
     * @return
     */
    public List<Object[]> getAvgTimeDeliveryHomeworksByStudent(Student student); 
    
    /**
     * La serie dei voti
     * relativi hai compiti
     * di ogni corso.
     * 
     * @param student
     * @return
     */
    public List<Object[]> getHomeworkScoreSeriesByStudent(Student student); 
    
    /**
     * La serie dei voti
     * relativi agli esami
     * di ogni corso.
     * 
     * @param student
     * @return
     */
    public List<Object[]> getExamScoreSeriesByStudent(Student student); 
    
    /**
     * La media dell presenze
     * per ogni corso.
     * 
     * @param student
     * @return
     */
    public List<Object[]> getAvgAttendanceByStudent(Student student); 
    
}