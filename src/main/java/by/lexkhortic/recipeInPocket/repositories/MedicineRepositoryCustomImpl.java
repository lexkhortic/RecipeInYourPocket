package by.lexkhortic.recipeInPocket.repositories;

import by.lexkhortic.recipeInPocket.models.Medicine;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MedicineRepositoryCustomImpl implements MedicineRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Medicine> findAllIDMedicinesByName(Set<String> medicinesName) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Medicine> queryMedicine = cb.createQuery(Medicine.class);
        Root<Medicine> medicine = queryMedicine.from(Medicine.class);

        Path<String> medicinePath = medicine.get("name");
        List<Predicate> predicates = new ArrayList<>();
        for (String name :medicinesName) {
            predicates.add(cb.like(medicinePath, name));
        }

        queryMedicine.select(medicine)
                .where(cb.or(predicates.toArray(new Predicate[predicates.size()])));

        return entityManager.createQuery(queryMedicine).getResultList();
    }
}
