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
- [ ] Aggiungere Input Username Register
- [ ] Mettere Input Username/Email Login
- [ ] Login With Google/Facebook/Apple/Github
- [ ] Fare le Api di Rankings
- [ ] Invertire Profile e Settings
- [ ] Inserire un Calendario Syncronizzato con Google/Outlook Calendar
- [ ] API Statistiche
- [ ] API Rankings
- [ ] Visualizzazione API Achievements