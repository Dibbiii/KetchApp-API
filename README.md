### Da Fare per tutte le tabelle del DB
```
CREATE POLICY "Select own user" ON public.users
FOR SELECT
USING (id = (select auth.uid()));
```


- Tomatoes Fatto
- Friends Fatto
- Appointments Fatto
- Activities Fatto
- Achievements Fatto
- Users 

## Todo List

- [ ] Mettere Forgot Password
- [ ] Sistemare i bottoni Username e Login With Google in login/register
- [ ] Mettere il bottone Login with Google nel Register
- [ ] Fare le Api di Rankings
- [ ] Invertire Profile e Settings
- [ ] Inserire un Calendario Syncronizzato con Google/Outlook Calendar
- [ ] API Statistiche
- [ ] API Rankings
- [ ] Visualizzazione API Achievements
- [ ] Integrare fotocamera
- [ ] Integrare libreria foto
- [ ] Aggiungere Notifiche
- [ ] Aggiungere i preferiti e i filtri
- [ ] Migliorare la Welcome Page
- [ ] Quando vado su Home con Account non Google mi continua a chiedere "Accedi con Google"
- [ ] Quando vado su Add Appointments con Account non Google mi continua a chiedere "Accedi con Google"
- [ ] Inserire il Fade Shadowing