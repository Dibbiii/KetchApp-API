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
- [ ] Fare le Api di Rankings
- [ ] Invertire Profile e Settings
- [ ] Inserire un Calendario Syncronizzato con Google/Outlook Calendar
- [ ] API Statistiche
- [ ] API Rankings
- [ ] Visualizzazione API Achievements